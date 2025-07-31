// 代码生成时间: 2025-07-31 17:12:09
 * A simple controller to calculate hash values for strings using the micronaut framework.
 *
 * @author Your Name
 * @since 1.0
 */
package com.example.hashcalculator;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.exceptions.HttpStatusException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nullable;

@Controller("/hash")
public class HashCalculatorController {

    // Supported hashing algorithms
    private static final String[] SUPPORTED_HASH_ALGORITHMS = {
        "MD5",
        "SHA-1",
        "SHA-256",
        "SHA-384",
        "SHA-512"
    };

    @Get("/{algorithm}/{data}")
    public HttpResponse<String> calculateHash(
            @PathVariable String algorithm,
            @PathVariable String data) {
        try {
            // Check if the requested algorithm is supported
            if (!isAlgorithmSupported(algorithm)) {
                throw new HttpStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Unsupported hashing algorithm: " + algorithm
                );
            }

            // Calculate hash using the requested algorithm
            String hash = calculateHashValue(data, algorithm);
            return HttpResponse.ok(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hashing algorithm not found: " + algorithm);
        } catch (Exception e) {
            throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error calculating hash: " + e.getMessage());
        }
    }

    /**
     * Checks if the provided algorithm is supported.
     *
     * @param algorithm The algorithm to check.
     * @return True if supported, false otherwise.
     */
    private boolean isAlgorithmSupported(@Nullable String algorithm) {
        for (String supportedAlgorithm : SUPPORTED_HASH_ALGORITHMS) {
            if (supportedAlgorithm.equalsIgnoreCase(algorithm)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the hash value for the given data using the specified algorithm.
     *
     * @param data The data to hash.
     * @param algorithm The hashing algorithm to use.
     * @return The calculated hash value.
     * @throws NoSuchAlgorithmException If the algorithm is not available.
     */
    private String calculateHashValue(String data, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hashBytes = digest.digest(data.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
