/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package org.apache.olingo.odata2.core.ep.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

// TODO: Auto-generated Javadoc
/**
 * Circular stream buffer to write/read into/from one single buffer.
 * With support of {@link InputStream} and {@link OutputStream} access to buffered data.
 * 
 * 
 */
public class CircleStreamBuffer {

  /** The Constant NEW_BUFFER_RESIZE_FACTOR. */
  private static final int NEW_BUFFER_RESIZE_FACTOR = 2;
  
  /** The Constant READ_EOF. */
  private static final int READ_EOF = -1;
  
  /** The Constant DEFAULT_CAPACITY. */
  private static final int DEFAULT_CAPACITY = 8192;
  
  /** The Constant MAX_CAPACITY. */
  private static final int MAX_CAPACITY = DEFAULT_CAPACITY * 32;

  /** The current allocate capacity. */
  private int currentAllocateCapacity = DEFAULT_CAPACITY;

  /** The write mode. */
  private boolean writeMode = true;
  
  /** The write closed. */
  private boolean writeClosed = false;
  
  /** The read closed. */
  private boolean readClosed = false;

  /** The buffer queue. */
  private Queue<ByteBuffer> bufferQueue = new LinkedBlockingQueue<ByteBuffer>();
  
  /** The current write buffer. */
  private ByteBuffer currentWriteBuffer;

  /** The in stream. */
  private InternalInputStream inStream;
  
  /** The out stream. */
  private InternalOutputStream outStream;

  /**
   * Creates a {@link CircleStreamBuffer} with default buffer size.
   */
  public CircleStreamBuffer() {
    this(DEFAULT_CAPACITY);
  }

  /**
   * Create a {@link CircleStreamBuffer} with given initial buffer size.
   *
   * @param initialCapacity initial capacity of internal buffer
   */
  public CircleStreamBuffer(final int initialCapacity) {
    currentAllocateCapacity = initialCapacity;
    createNewWriteBuffer();
    inStream = new InternalInputStream(this);
    outStream = new InternalOutputStream(this);
  }

  /**
   * Get {@link InputStream} for data read access.
   * 
   * @return the stream
   */
  public InputStream getInputStream() {
    return inStream;
  }

  /**
   * Get {@link OutputStream} for write data.
   * 
   * @return the stream
   */
  public OutputStream getOutputStream() {
    return outStream;
  }

  // #############################################
  // #
  // # Common parts
  // #
  // #############################################

  /**
   * Closes the write (input) part of the {@link CircleStreamBuffer}.
   * After this call the buffer can only be read out.
   */
  public void closeWrite() {
    writeClosed = true;
  }

  /**
   * Closes the read (output) part of the {@link CircleStreamBuffer}.
   * After this call it is possible to write into the buffer (but can never be read out).
   */
  public void closeRead() {
    readClosed = true;
    // clear references to byte buffers
    ByteBuffer buffer = bufferQueue.poll();
    while (buffer != null) {
      buffer.clear();
      buffer = bufferQueue.poll();
    }
  }

  /**
   * Closes write and read part (and hence the complete buffer).
   */
  public void close() {
    closeWrite();
    closeRead();
  }

  /**
   * Remaining.
   *
   * @return the int
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private int remaining() throws IOException {
    if (writeMode) {
      return currentWriteBuffer.remaining();
    } else {
      ByteBuffer toRead = getReadBuffer();
      if (toRead == null) {
        return 0;
      }
      return toRead.remaining();
    }
  }

  // #############################################
  // #
  // # Reading parts
  // #
  // #############################################

  /**
   * Gets the read buffer.
   *
   * @return the read buffer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private ByteBuffer getReadBuffer() throws IOException {
    if (readClosed) {
      throw new IOException("Tried to read from closed stream.");
    }

    boolean next = false;
    ByteBuffer tmp = null;
    if (writeMode) {
      writeMode = false;
      next = true;
    } else {
      tmp = bufferQueue.peek();
      if (tmp != null && !tmp.hasRemaining()) {
        tmp = bufferQueue.poll();
        next = true;
      }
    }

    if (next) {
      tmp = bufferQueue.peek();
      if (tmp != null) {
        tmp.flip();
      }
      tmp = getReadBuffer();
    }

    return tmp;
  }

  /**
   * Read.
   *
   * @param b the b
   * @param off the off
   * @param len the len
   * @return the int
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private int read(final byte[] b, final int off, final int len) throws IOException {
    ByteBuffer readBuffer = getReadBuffer();
    if (readBuffer == null) {
      return READ_EOF;
    }

    int toReadLength = readBuffer.remaining();
    if (len < toReadLength) {
      toReadLength = len;
    }
    readBuffer.get(b, off, toReadLength);
    return toReadLength;
  }

  /**
   * Read.
   *
   * @return the int
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private int read() throws IOException {
    ByteBuffer readBuffer = getReadBuffer();
    if (readBuffer == null) {
      return READ_EOF;
    }

    return readBuffer.get();
  }

  // #############################################
  // #
  // # Writing parts
  // #
  // #############################################

  /**
   * Write.
   *
   * @param data the data
   * @param off the off
   * @param len the len
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void write(final byte[] data, final int off, final int len) throws IOException {
    ByteBuffer writeBuffer = getWriteBuffer(len);
    writeBuffer.put(data, off, len);
  }

  /**
   * Gets the write buffer.
   *
   * @param size the size
   * @return the write buffer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private ByteBuffer getWriteBuffer(final int size) throws IOException {
    if (writeClosed) {
      throw new IOException("Tried to write into closed stream.");
    }

    if (writeMode) {
      if (remaining() < size) {
        createNewWriteBuffer(size);
      }
    } else {
      writeMode = true;
      createNewWriteBuffer();
    }

    return currentWriteBuffer;
  }

  /**
   * Write.
   *
   * @param b the b
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void write(final int b) throws IOException {
    ByteBuffer writeBuffer = getWriteBuffer(1);
    writeBuffer.put((byte) b);
  }

  /**
   * Creates the new write buffer.
   */
  private void createNewWriteBuffer() {
    createNewWriteBuffer(currentAllocateCapacity);
  }

  /**
   * Creates a new buffer (per {@link #allocateBuffer(int)}) with the requested capacity as minimum capacity, add the
   * new allocated
   * buffer to the {@link #bufferQueue} and set it as {@link #currentWriteBuffer}.
   * 
   * @param requestedCapacity minimum capacity for new allocated buffer
   */
  private void createNewWriteBuffer(final int requestedCapacity) {
    ByteBuffer b = allocateBuffer(requestedCapacity);
    bufferQueue.add(b);
    currentWriteBuffer = b;
  }

  /**
   * Allocate a new buffer with requested capacity.
   *
   * @param requestedCapacity minimal capacity of new buffer
   * @return the buffer
   */
  private ByteBuffer allocateBuffer(final int requestedCapacity) {
    if (requestedCapacity > MAX_CAPACITY) {
      currentAllocateCapacity = MAX_CAPACITY;
      return ByteBuffer.allocate(requestedCapacity);
    }

    if (requestedCapacity <= currentAllocateCapacity) {
      currentAllocateCapacity *= NEW_BUFFER_RESIZE_FACTOR;
      if (currentAllocateCapacity > MAX_CAPACITY) {
        currentAllocateCapacity = MAX_CAPACITY;
      }
    } else {
      currentAllocateCapacity = requestedCapacity;
    }

    return ByteBuffer.allocate(currentAllocateCapacity);
  }

  // #############################################
  // #
  // # Inner classes (streams)
  // #
  // #############################################

  /**
   * The Class InternalInputStream.
   */
  private static class InternalInputStream extends InputStream {

    /** The in buffer. */
    private final CircleStreamBuffer inBuffer;

    /**
     * Instantiates a new internal input stream.
     *
     * @param csBuffer the cs buffer
     */
    public InternalInputStream(final CircleStreamBuffer csBuffer) {
      inBuffer = csBuffer;
    }

    /**
     * Available.
     *
     * @return the int
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public int available() throws IOException {
      return inBuffer.remaining();
    }

    /**
     * Read.
     *
     * @return the int
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public int read() throws IOException {
      return inBuffer.read();
    }

    /**
     * Read.
     *
     * @param b the b
     * @param off the off
     * @param len the len
     * @return the int
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
      return inBuffer.read(b, off, len);
    }

    /**
     * Close.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void close() throws IOException {
      inBuffer.closeRead();
    }
  }

  /**
   * The Class InternalOutputStream.
   */
  private static class InternalOutputStream extends OutputStream {
    
    /** The out buffer. */
    private final CircleStreamBuffer outBuffer;

    /**
     * Instantiates a new internal output stream.
     *
     * @param csBuffer the cs buffer
     */
    public InternalOutputStream(final CircleStreamBuffer csBuffer) {
      outBuffer = csBuffer;
    }

    /**
     * Write.
     *
     * @param b the b
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void write(final int b) throws IOException {
      outBuffer.write(b);
    }

    /**
     * Write.
     *
     * @param b the b
     * @param off the off
     * @param len the len
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
      outBuffer.write(b, off, len);
    }

    /**
     * Close.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void close() throws IOException {
      outBuffer.closeWrite();
    }
  }
}
