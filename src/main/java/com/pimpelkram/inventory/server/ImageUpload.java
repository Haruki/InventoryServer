package com.pimpelkram.inventory.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import spark.utils.IOUtils;

public class ImageUpload {

    private final String imagesFolger;

    Logger               logger = LoggerFactory.getLogger(ImageUpload.class);

    public ImageUpload(String directory) {
        this.imagesFolger = directory + "images/";
    }

    public String post(Request req, Response resp) {
        req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(this.imagesFolger));
        Optional<Part> filePart = Optional.empty();
        try {
            filePart = req.raw().getParts().stream().filter(p -> p.getContentType().matches("^image.*")).findFirst();
        } catch (IOException | ServletException e1) {
            e1.printStackTrace();
        }
        if (!filePart.isPresent()) {
            return "No image uploaded!";
        } else {
            OutputStream outputStream = null;
            String newImageUrl = null;
            String givenFileName = null;
            try (InputStream inputStream = filePart.get().getInputStream()) {
                givenFileName = filePart.get().getSubmittedFileName();
                givenFileName = new File(givenFileName).getName();
                newImageUrl = this.imagesFolger + givenFileName;
                outputStream = new FileOutputStream(newImageUrl);
                IOUtils.copy(inputStream, outputStream);
            } catch (final IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
            return givenFileName;
        }
    }
}
