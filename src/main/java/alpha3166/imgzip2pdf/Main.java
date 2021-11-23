package alpha3166.imgzip2pdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String... args) throws Exception {
		var self = new Main();
		self.execute(args);
	}

	public void execute(String... args) throws Exception {
		var opt = new OptionHandler(args);
		if (opt.abort()) {
			return;
		}

		var zipMap = opt.zipMap();
		for (var zipPath : zipMap.keySet()) {
			logger.info(zipPath.toString());
			var pdfPath = zipMap.get(zipPath);
			var zipHandler = new ZipHandler(zipPath);
			var imagePathList = zipHandler.getImagePathList();
			PdfHandler.createPdfFromImages(pdfPath, imagePathList, opt.isRightToLeft());
			zipHandler.close();
		}
	}
}
