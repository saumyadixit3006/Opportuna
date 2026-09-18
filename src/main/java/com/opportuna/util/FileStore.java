package com.opportuna.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public final class FileStore {
    private FileStore() {
    }

    public static <T> List<T> readList(Path path, Class<T> type) {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            Object value = in.readObject();
            if (!(value instanceof List<?> list)) {
                throw new IllegalStateException("Invalid data file: " + path);
            }

            List<T> result = new ArrayList<>();
            for (Object item : list) {
                if (!type.isInstance(item)) {
                    throw new IllegalStateException("Unexpected data in " + path);
                }
                result.add(type.cast(item));
            }
            return result;
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Could not read " + path, e);
        }
    }

    public static synchronized <T extends Serializable> void writeList(Path path, List<T> data) {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            Path temp = Path.of(path.toString() + ".tmp");
            try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(temp))) {
                out.writeObject(new ArrayList<>(data));
            }

            Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Could not write " + path, e);
        }
    }
}
