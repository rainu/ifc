package de.rainu.lib.ifc;

import java.io.Serializable;
import java.util.Set;

import de.rainu.lib.dbc.DBSet;
import de.rainu.lib.dbc.beans.ConnectionInfo;

/**
 * Das IF(InFIle)-Set ist eine {@link Set} Implementierung.
 * 
 * @author rainu
 *
 * @param <E>
 */
public class IFSet<E extends Serializable> extends DBSet<E> {
	public IFSet(String filePath, String tableName, boolean dropIfExist,
			boolean debugMode) {
		super(new ConnectionInfo(
				"org.h2.Driver", 
				"jdbc:h2:" + filePath, 
				"sa", 
				""), 
			tableName, dropIfExist, debugMode);
	}

	public IFSet(String filePath, String tableName, boolean dropIfExist) {
		this(filePath, tableName, dropIfExist, false);
	}

	public IFSet(String filePath, String tableName) {
		// Ich geh davon aus, wenn man diese beiden Werte schon definiert, dass
		// man auch möchte,
		// dass die Tabelle nicht gelöscht wird
		this(filePath, tableName, false);
	}

	public IFSet(String filePath) {
		// Standarmäßig soll die Tabelle geleert werden, fals sie schon
		// existiert
		this(filePath, null, true, false);
	}

	public IFSet() {
		// Unter default Temp-Verzeichnis hinterlegen
		this(System.getProperty("java.io.tmpdir") + "/IFC", null, true, false);
	}
}
