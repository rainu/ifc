package de.raysha.lib.ifc;

import java.util.List;

import de.raysha.lib.dbc.DBList;
import de.raysha.lib.dbc.beans.ConnectionInfo;

/**
 * Das IF(InFIle)-List ist eine {@link List} Implementierung.
 *
 * @author rainu
 *
 * @param <E>
 */
public class IFList<E> extends DBList<E> {
	public IFList(String filePath, String tableName, boolean dropIfExist,
			boolean debugMode) {
		super(new ConnectionInfo(
				"org.h2.Driver",
				"jdbc:h2:" + filePath,
				"sa",
				""),
			tableName, dropIfExist, debugMode);
	}

	public IFList(String filePath, String tableName, boolean dropIfExist) {
		this(filePath, tableName, dropIfExist, false);
	}

	public IFList(String filePath, String tableName) {
		// Ich geh davon aus, wenn man diese beiden Werte schon definiert, dass
		// man auch möchte,
		// dass die Tabelle nicht gelöscht wird
		this(filePath, tableName, false);
	}

	public IFList(String filePath) {
		// Standarmäßig soll die Tabelle geleert werden, fals sie schon
		// existiert
		this(filePath, null, true, false);
	}

	public IFList() {
		// Unter default Temp-Verzeichnis hinterlegen
		this(System.getProperty("java.io.tmpdir") + "/IFC", null, true, false);
	}

}
