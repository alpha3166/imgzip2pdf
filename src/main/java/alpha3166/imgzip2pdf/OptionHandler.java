package alpha3166.imgzip2pdf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class OptionHandler {
	private boolean abort;
	private Map<Path, Path> zipMap;
	private boolean rightToLeft;

	public OptionHandler(String... args) throws IOException, ParseException {
		var options = new Options();
		options.addOption("h", "display this help and exit");
		options.addOption("d", true, "output directory");
		options.addOption("r", "set direction to right-to-left");

		// Parse
		var cmd = new DefaultParser().parse(options, args);

		// Handle -h
		if (cmd.hasOption("h")) {
			new HelpFormatter().printHelp("java -jar IMGZIP2PDF_JAR [OPTION]... TARGET_ZIP...",
					"Create PDF from images in the Zip", options, null);
			abort = true;
			return;
		}

		// Handle arguments
		zipMap = new TreeMap<>();
		for (var arg : cmd.getArgs()) {
			var zipPath = Paths.get(arg);
			if (!Files.isRegularFile(zipPath)) {
				throw new NoSuchFileException(zipPath.toString());
			}
			var pdfPath = Paths.get(arg.replaceFirst("(\\.\\w+)?$", ".pdf"));
			zipMap.put(zipPath, pdfPath);
		}

		// Handle -d
		if (cmd.hasOption("d")) {
			var outDir = Paths.get(cmd.getOptionValue("d"));
			if (!Files.isDirectory(outDir)) {
				throw new NoSuchFileException("-d " + cmd.getOptionValue("d"));
			}
			zipMap.replaceAll((k, v) -> outDir.resolve(v.getFileName()));
		}

		// Handle -r
		rightToLeft = cmd.hasOption("r");
	}

	public boolean abort() {
		return abort;
	}

	public Map<Path, Path> zipMap() {
		return zipMap;
	}

	public boolean isRightToLeft() {
		return rightToLeft;
	}
}
