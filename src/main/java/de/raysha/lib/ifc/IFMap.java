package de.raysha.lib.ifc;

import java.util.Map;

import de.raysha.lib.dbc.DBMap;
import de.raysha.lib.dbc.beans.ConnectionInfo;

/**
 * Die IF(InFile)-Map ist eine {@link Map} Implementierung.
 * 
 * @author rainu
 *
 * @param <K> Typ des Schlüssels
 * @param <V> Typ des Wertes
 */
public class IFMap<K, V> extends DBMap<K, V> {
	public IFMap(String filePath, String tableName, boolean dropIfExist,
			boolean debugMode) {
		super(new ConnectionInfo(
				"org.h2.Driver", 
				"jdbc:h2:" + filePath, 
				"sa", 
				""), 
			tableName, dropIfExist, debugMode);
		
		//da wir in der h2 Datenbank Exklusiv-Zugriff haben, können wir getrost
		//die Größe zwischenspeichern.
		this.cacheSize(true);
	}

	public IFMap(String filePath, String tableName, boolean dropIfExist) {
		this(filePath, tableName, dropIfExist, false);
	}

	public IFMap(String filePath, String tableName) {
		// Ich geh davon aus, wenn man diese beiden Werte schon definiert, dass
		// man auch möchte,
		// dass die Tabelle nicht gelöscht wird
		this(filePath, tableName, false);
	}

	public IFMap(String filePath) {
		// Standarmäßig soll die Tabelle geleert werden, fals sie schon
		// existiert
		this(filePath, null, true, false);
	}

	public IFMap() {
		// Unter default Temp-Verzeichnis hinterlegen
		this(System.getProperty("java.io.tmpdir") + "/IFC", null, true, false);
	}
}
