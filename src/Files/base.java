package Files;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.annotations.BeforeSuite;

public class base {

	Properties prop;
public static org.apache.logging.log4j.Logger log;
	
	@BeforeSuite
	public void bsuite() throws FileNotFoundException, IOException
	{
		String log4jconfigfile = "C:\\sree\\MyAPIProject\\src\\Files\\log4j.xml";
		ConfigurationSource source = new ConfigurationSource(new FileInputStream(log4jconfigfile));
		Configurator.initialize(null, source);
		log = LogManager.getLogger(base.class.getName());
	}
}
