package org.diretto.api.client.external.processing;

import org.apache.commons.configuration.XMLConfiguration;
import org.diretto.api.client.service.AbstractServicePluginID;
import org.diretto.api.client.service.Service;
import org.diretto.api.client.util.ConfigUtils;

/**
 * This class serves for the identification of the {@link ProcessingService}.
 * <br/><br/>
 * 
 * <i>Annotation:</i> <u>Singleton Pattern</u>
 * 
 * @author Tobias Schlecht
 */
public final class ProcessingServiceID extends AbstractServicePluginID
{
	private static final String CONFIG_FILE = "org/diretto/api/client/external/processing/config.xml";

	private static final XMLConfiguration xmlConfiguration = ConfigUtils.getXMLConfiguration(CONFIG_FILE);

	public static final ProcessingServiceID INSTANCE = new ProcessingServiceID(xmlConfiguration.getString("name"), xmlConfiguration.getString("api-version"), getInitServiceClass());

	/**
	 * Constructs the sole instance of the {@link ProcessingServiceID}.
	 * <br/><br/>
	 * 
	 * <i>Annotation:</i> <u>Singleton Pattern</u>
	 */
	private ProcessingServiceID(String name, String apiVersion, Class<Service> serviceClass)
	{
		super(name, apiVersion, serviceClass);
	}

	/**
	 * Returns the implementation class of the {@link ProcessingService}, which
	 * is loaded from the XML configuration file.
	 * 
	 * @return The implementation class of the {@code ProcessingService}
	 */
	@SuppressWarnings("unchecked")
	private static Class<Service> getInitServiceClass()
	{
		try
		{
			return (Class<Service>) Class.forName(xmlConfiguration.getString("service-class"));
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the {@link XMLConfiguration} object, which is loaded from the XML
	 * configuration file corresponding to the whole {@link ProcessingService}
	 * implementation.
	 * 
	 * @return The {@code XMLConfiguration} object
	 */
	XMLConfiguration getXMLConfiguration()
	{
		return xmlConfiguration;
	}
}
