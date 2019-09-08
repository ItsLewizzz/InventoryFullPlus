package me.lewis.inventoryfull.update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

import me.lewis.inventoryfull.InventoryFullPlus;

public class UpdateManager {
	
	private final InventoryFullPlus plugin;
	
	private boolean isLatestVersion = false;
	private String newVersion;
	private String currentVersion;
	
	private final String KEY = "key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=";
	
	public UpdateManager(InventoryFullPlus plugin) {
		this.plugin = plugin;
    }
	
	public void checkForUpdate() {
		try {	
            HttpURLConnection connection = (HttpURLConnection) new URL("https://www.spigotmc.org/api/general.php").openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.getOutputStream().write((KEY + 31544).getBytes(StandardCharsets.UTF_8));
            
            String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            newVersion = version;
            
            currentVersion = plugin.getDescription().getVersion();
            
            if (version.equals(getCurrentVersion())) isLatestVersion = true;
          
        
        }catch (IOException e) {
        	plugin.getLogger().log(Level.WARNING, "Could not make connection to SpigotMC.org");
        }
	}
	
	public boolean isLatestVersion() {
		return isLatestVersion;
	}

	public String getNewVersion() {
		return newVersion;
	}
	
	public String getCurrentVersion() {
		return currentVersion;
	}

}
