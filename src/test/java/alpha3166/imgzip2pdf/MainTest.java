package alpha3166.imgzip2pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;

class MainTest {
	List<String> logs = LogAppender.logs;
	Path base;
	Main sut;

	@BeforeEach
	void setUp() throws Exception {
		logs.clear();
		base = DataManager.makeTestDir();
		sut = new Main();
	}

	@AfterEach
	void tearDown() throws Exception {
		DataManager.removeDir(base);
	}

	@Test
	void test() throws Exception {
		// Setup
		DataManager.generateZip(base.resolve("sample.zip"));
		// Exercise
		sut.execute(base + "/sample.zip");
		// Verify
		var pdfReader = new PdfReader(base.resolve("sample.pdf").toFile());
		var pdfDoc = new PdfDocument(pdfReader);
		assertEquals(4, pdfDoc.getNumberOfPages());
		assertEquals(PdfName.TwoPageRight, pdfDoc.getCatalog().getPageLayout());
		assertNull(pdfDoc.getCatalog().getViewerPreferences());
		pdfDoc.close();
		assertEquals(1, logs.size());
		assertEquals(base.resolve("sample.zip").toString(), logs.get(0));
	}
}
