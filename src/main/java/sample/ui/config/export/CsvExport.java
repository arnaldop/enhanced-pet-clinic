package sample.ui.config.export;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;

import com.github.dandelion.core.DandelionException;
import com.github.dandelion.datatables.core.export.DatatablesExport;
import com.github.dandelion.datatables.core.export.ExportConf;
import com.github.dandelion.datatables.core.export.ReservedFormat;
import com.github.dandelion.datatables.core.html.HtmlColumn;
import com.github.dandelion.datatables.core.html.HtmlRow;
import com.github.dandelion.datatables.core.html.HtmlTable;

public class CsvExport implements DatatablesExport {

	private static final String SEPARATOR_CHAR = ",";
	private HtmlTable table;
	private ExportConf exportConf;

	@Override
	public void initExport(HtmlTable table) {
		this.table = table;
		this.exportConf = table.getTableConfiguration().getExportConfigurations().get(ReservedFormat.CSV);
	}

	private String escapeField(StringBuilder value) {
		if (StringUtils.isBlank(value)) {
			return "";
		} else {
			return value.toString().replaceAll("\"", "\"\"");
		}
	}

	@Override
	public void processExport(OutputStream output) {
		StringBuilder buffer = new StringBuilder();

		if (exportConf.getIncludeHeader()) {
			for (HtmlRow row : table.getHeadRows()) {
				for (HtmlColumn column : row.getColumns(ReservedFormat.ALL, ReservedFormat.CSV)) {
					buffer.append("\"").append(escapeField(column.getContent())).append("\"").append(SEPARATOR_CHAR);
				}
				buffer.append("\n");
			}
		}
		for (HtmlRow row : table.getBodyRows()) {
			for (HtmlColumn column : row.getColumns(ReservedFormat.ALL, ReservedFormat.CSV)) {
				buffer.append("\"").append(escapeField(column.getContent())).append("\"").append(SEPARATOR_CHAR);
			}
			buffer.append("\n");
		}

		try {
			output.write(buffer.toString().getBytes());
		} catch (IOException e) {
			StringBuilder sb = new StringBuilder("Something went wrong during the CSV generation of the table '");
			sb.append(table.getOriginalId());
			sb.append("' and with the following export configuration: ");
			sb.append(exportConf.toString());
			throw new DandelionException(sb.toString(), e);
		}
	}
}