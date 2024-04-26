package Hotel.Utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * General class for miscellaneous and / or widely-used functionality
 * @author Rafe Loya
 */
public class Utilities {
    /**
     *     Set of primes that can be used for hashcode generation
     */
    public static final int[] primes = { 2, 79, 191, 311, 439, 577, 709, 857, 1009, 1151, 1297 };

    /**
     * <p>
     *     Creates a BufferedImage instance from a resource url (to file in ./resources folder).
     *     If the image cannot be found in the directory or the given string is null / empty, a default image,
     *     "missing_texture.png", is loaded instead.
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
}