package se.miun.lior2300.dt031g.dialer;

import android.content.Context;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Util {
    private static final String TAG = "Util";

    /**
     * The directory in which all voices for the Dialer app are stored (in the app-specific
     * internal storage location).
     */
    public static final String VOICE_DIR = "voices";

    /**
     * The default voice to be used by the Dialer app.
     */
    public static final String DEFAULT_VOICE = "mamacita_us";

    /**
     * The file extension for the default voice files used by the Dialer app.
     */
    public static final String DEFAULT_VOICE_EXTENSION = "mp3";

    /**
     * The resource ids for the default voice (in res/raw).
     */
    public static final int[] DEFAULT_VOICE_RESOURCE_IDS = {
            R.raw.zero, R.raw.one, R.raw.two, R.raw.three,
            R.raw.four, R.raw.five, R.raw.six, R.raw.seven,
            R.raw.eight, R.raw.nine, R.raw.star, R.raw.pound
    };

    /**
     * The file names for each sound in a voice, mapped to its corresponding button title.
     */
    public static final Map<String, String> DEFAULT_VOICE_FILE_NAMES= new HashMap<>();

    static {
        DEFAULT_VOICE_FILE_NAMES.put("0", "zero.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("1", "one.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("2", "two.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("3", "three.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("4", "four.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("5", "five.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("6", "six.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("7", "seven.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("8", "eight.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("9", "nine.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("*", "star.mp3");
        DEFAULT_VOICE_FILE_NAMES.put("#", "pound.mp3");
    }

    /**
     * Returns the absolute path to the directory on the filesystem where app-specific files
     * are stored.
     * @param context an application context
     * @return The path of the directory holding application files.
     */
    public static File getInternalStorageDir(Context context) {
        return context.getFilesDir();
    }

    /**
     * Returns the absolute path to the directory containing the voice files for the
     * given voice name.
     * @param context an application context
     * @param voiceName the name of the voice to get the directory for
     * @return The path of the directory for holding the voice given by <code>voiceName</code>.
     */
    public static File getDirForVoice(Context context, String voiceName) {
        if (voiceName == null || voiceName.length() < 1) {
            voiceName = DEFAULT_VOICE;
        }

        return new File(getInternalStorageDir(context), VOICE_DIR + File.separator + voiceName);
    }

    /**
     * Returns the absolute path to the directory containing the default voice files.
     * @param context an application context
     * @return The path of the directory holding the default voice.
     */
    public static File getDirForDefaultVoice(Context context) {
        return getDirForVoice(context, DEFAULT_VOICE);
    }

    /**
     * Copies each resource in <code>DEFAULT_VOICE_RESOURCE_IDS</code> to the directory returned
     * by <code>getDirForDefaultVoice</code>.
     * @param context an application context
     * @return true if all files are copied, or false if an error occurs.
     */
    public static boolean copyDefaultVoiceToInternalStorage(Context context) {
        // Complete path to the dir of the default voice
        File voiceDir = getDirForDefaultVoice(context);

        if (!voiceDir.exists()) {
            if (!voiceDir.mkdirs()) {
                Log.e(TAG, "Could not create dir: " + voiceDir);
                return false;
            }
        }

        for (int resourceId: DEFAULT_VOICE_RESOURCE_IDS) {
            String filename = context.getResources().getResourceEntryName(resourceId) + "." + DEFAULT_VOICE_EXTENSION; // ex. "one.mp3"

            try (InputStream in = context.getResources().openRawResource(resourceId);
                    OutputStream out = new FileOutputStream(new File(voiceDir, filename))) {
                // Copy from in to out using a byte buffer
                byte[] buffer = new byte[2048];

                int bytesRead;
                while ((bytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesRead);
                }

            } catch (IOException e) {
                Log.e(TAG, "Error copying file: " + e.getMessage());
                return false;
            }
        }

        return true; // all files have been copied
    }
	
	 /**
	 * Unzips (decompresses) the contents of a source zip file to a destination directory.
	 * @param sourceFile a zip file to be unzipped to the destination directory
	 * @param destinationDir a directory in which the contents of the zip file are to be unzipped
	 * @return true if the zip file is successfully unzipped
	 */
	public static boolean unzip(File sourceFile, File destinationDir) {
		try {
			// Open the source ZIP file for reading
			ZipFile zipFile = new ZipFile(sourceFile);

			// Get content of ZIP file (as an enumeration of ZipEntry)
			Enumeration<? extends ZipEntry> e = zipFile.entries();

			// As long as there are more entries to handle
			while (e.hasMoreElements()) {
				// Get the next entry
				ZipEntry entry = (ZipEntry) e.nextElement();

				File destinationFile = new File(destinationDir, entry.getName());

				// Create destination folders (if any)
				File destinationParent = destinationFile.getParentFile();

				if (destinationParent != null) {
					destinationParent.mkdirs();
				}

				// Do the unzipping, but only if the entry is a file
				if (!entry.isDirectory()) {
					// A BufferReader to read the entry from the zip file
					BufferedInputStream in = new BufferedInputStream(
							zipFile.getInputStream(entry));

					// A BufferedOutputStream to save the entry to destination
					BufferedOutputStream out = new BufferedOutputStream(
							new FileOutputStream(destinationFile));

					// Create a buffer to avoid writing byte for byte
					byte[] buffer = new byte[4096];
					int bytesWrite;

					// Writes an array of bytes from the zip to the destination
					while ((bytesWrite = in.read(buffer)) != -1) {
						out.write(buffer, 0, bytesWrite);
					}

					// Close streams used
					out.flush();
					out.close();
					in.close();
				}
			}

			// Close the zip file
			zipFile.close();
		}
		catch (IOException e) {
			Log.e(TAG, "Error unzipping " + sourceFile + ": " + e.toString());
			return false;
		}

		return true;
	}

    /**
     * Check if the default voice files from res/raw already exists in the directory returned
     * by <code>getDirForDefaultVoice</code>.
     * @param context an application context
     * @return true if the default voice exists in internal app-specific storage, false if not.
     */
    public static boolean defaultVoiceExist(Context context) {
		var defaultStorage = getDirForDefaultVoice(context);

        if(!defaultStorage.exists()) {
            return false;
        }

        for (String voiceFile : DEFAULT_VOICE_FILE_NAMES.values()) {
            File file = new File(defaultStorage, voiceFile);

            if(!file.exists()) {
                return false;
            }
        }

        return true;
    }
}
