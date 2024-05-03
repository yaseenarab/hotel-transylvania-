package Hotel.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * General class for miscellaneous and / or widely-used functionality
 * @author Rafe Loya
 */
public class Utilities {
    private static final Logger UTIL_Logger =
            Logger.getLogger(Utilities.class.getName());

    /**
     *     Set of primes that can be used for hashcode generation
     */
    public static final int[] primes = { 2, 79, 191, 311, 439, 577, 709, 857, 1009, 1151, 1297 };

    /**
     * <p>
     *     Creates a BufferedImage instance from a resource url (to file in ./resources folder).
     *     If the given string is null / empty, a default image, "missing_texture.png", is loaded instead.
     * </p>
     * @param obj Class that is currently attempting to generate an image
     * @param url Name of the image to get from ./resources
     * @return BufferedImage instance generated from given url, or a default error image
     * @throws IOException Error occurs while reading or when failing to create an ImageInputStream
     * @author Rafe Loya
     */
    public static BufferedImage generateImage(Object obj, String url) throws IOException {
        if (url == null || url.isEmpty()) { url = "missing_texture.png"; }
        return ImageIO.read(
                new File(
                        Objects.requireNonNull(
                                obj.getClass()
                                        .getResource("/" + url)).getFile()));
    }

    /**
     * <p>
     *     Creates an Image instance from a resource url (to file in ./resources folder).
     *     If the given string is null / empty, a default image, "missing_texture.png", is loaded instead.
     *     If either dimension is null or equal to 0, then this function works similarly to generateImage,
     *     but returns an Image instead of a BufferedImage
     * </p>
     * @param obj    Class that is currently attempting to generate an image
     * @param url    Name of the image to get from ./resources
     * @param width  Dimension of image in terms of the x-axis
     * @param height Dimension of image in terms of the y-axis
     * @return Image instance of given url, with dimension specified or in its original dimensions
     * @throws IOException Error occurs while reading or when failing to create an ImageInputStream
     * @author Rafe Loya
     */
    public static Image generateScaledImage(Object obj, String url, Integer width, Integer height) throws IOException {
        // If any condition is true, image will be generated with original dimensions
        if (width == null ||  width == 0 || height == null || height == 0) {
            width = height = -1;
        }

        BufferedImage originalImg;
        try {
            originalImg = generateImage(obj, url);
        } catch (IOException e) {
            UTIL_Logger.severe("Unable to load image : " + url);
            throw new IOException();
        }
        return originalImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}