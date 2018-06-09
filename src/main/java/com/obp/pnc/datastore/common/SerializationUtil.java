package com.obp.pnc.datastore.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

public final class SerializationUtil {

    private SerializationUtil() {

    }

    public static void serialize(List<String> partyIds) {

        try (FileOutputStream file = new FileOutputStream("partyIds.ser")) {

            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(partyIds);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<String> partyIds() {

        try (FileInputStream file = new FileInputStream("partyIds.ser")) {
            ObjectInputStream in = new ObjectInputStream(file);
            return (List<String>) in.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }


}
