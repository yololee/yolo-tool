package com.yolo.common.support.utils.web;

import com.yolo.common.support.utils.exception.ExceptionUtil;
import com.yolo.common.support.utils.string.Charsets;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IoUtil
 */
public class IoUtil extends org.springframework.util.StreamUtils {

	/**
	 * closeQuietly
	 *
	 * @param closeable 自动关闭
	 */
	public static void closeQuietly(@Nullable Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * InputStream to String utf-8
	 *
	 * @param input the <code>InputStream</code> to read from
	 * @return the requested String
	 */
	public static String toString(InputStream input) {
		return toString(input, Charsets.UTF_8);
	}

	/**
	 * InputStream to String
	 *
	 * @param input   the <code>InputStream</code> to read from
	 * @param charset the <code>Charsets</code>
	 * @return the requested String
	 */
	public static String toString(@Nullable InputStream input, java.nio.charset.Charset charset) {
		try {
			return StreamUtils.copyToString(input, charset);
		} catch (IOException e) {
			throw ExceptionUtil.unchecked(e);
		} finally {
			IoUtil.closeQuietly(input);
		}
	}

	public static byte[] toByteArray(@Nullable InputStream input) {
		try {
			return StreamUtils.copyToByteArray(input);
		} catch (IOException e) {
			throw ExceptionUtil.unchecked(e);
		} finally {
			IoUtil.closeQuietly(input);
		}
	}

	/**
	 * Writes chars from a <code>String</code> to bytes on an
	 * <code>OutputStream</code> using the specified character encoding.
	 * <p>
	 * This method uses {@link String#getBytes(String)}.
	 *
	 * @param data     the <code>String</code> to write, null ignored
	 * @param output   the <code>OutputStream</code> to write to
	 * @param encoding the encoding to use, null means platform default
	 * @throws IOException if an I/O error occurs
	 */
	public static void write(@Nullable final String data, final OutputStream output, final java.nio.charset.Charset encoding) throws IOException {
		if (data != null) {
			output.write(data.getBytes(encoding));
		}
	}
}
