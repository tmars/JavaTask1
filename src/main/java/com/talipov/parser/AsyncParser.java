package com.talipov.parser;

import com.talipov.ParserErrorException;
import com.talipov.ResourceNotFoundException;

import javax.management.QueryEval;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Марсель on 20.02.2017.
 */
public class AsyncParser extends Parser {

    /**
     * Входные данные
     */
    private Scanner input;

    public AsyncParser(String filename) throws ResourceNotFoundException {
        super(filename);

        Path path = Paths.get(filename);
        int filesize;
        AsynchronousFileChannel ch;
        try {
            filesize = (int)Files.size(path);
            ch = AsynchronousFileChannel.open(path);
        } catch (IOException e) {
            throw new ResourceNotFoundException();
        }

        int count = 2;
        int part = filesize / count;

        ArrayList<ByteBuffer> buffers = new ArrayList<>(count);
        ArrayList<Future> results = new ArrayList<>();

        for (int i = 0, start = 0; i < count; i++, start += part) {
            ByteBuffer buffer = ByteBuffer.allocate(i == count-1 ? filesize - start : part);
            results.add(ch.read(buffer, start));
            buffers.add(buffer);
        }

        StringBuffer content = new StringBuffer("");
        for (int i = 0; i < results.size(); i++) {
            while (!results.get(i).isDone());
            content.append(new String(buffers.get(i).array()));
        }

        input = new Scanner(content.toString());
    }

    @Override
    public Integer getNext() throws ParserErrorException {
        if (input.hasNext()) {
            if (input.hasNextInt()) {
                return input.nextInt();
            } else {
                String s = input.next();
                input.close();

                throw new ParserErrorException(s);
            }
        } else {
            input.close();
        }

        return null;
    }
}
