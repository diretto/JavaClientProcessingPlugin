package org.diretto.api.client.external.processing;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.diretto.api.client.JavaClient;
import org.diretto.api.client.JavaClientImpl;
import org.diretto.api.client.base.annotations.InvocationLimited;
import org.diretto.api.client.base.data.MediaTypeFactory;
import org.diretto.api.client.base.external.JacksonConverter;
import org.diretto.api.client.base.types.LoadType;
import org.diretto.api.client.main.core.CoreService;
import org.diretto.api.client.main.core.entities.Attachment;
import org.diretto.api.client.main.core.entities.AttachmentID;
import org.diretto.api.client.main.core.entities.Document;
import org.diretto.api.client.main.core.entities.DocumentID;
import org.diretto.api.client.service.AbstractService;
import org.diretto.api.client.util.InvocationUtils;
import org.diretto.api.client.util.URLTransformationUtils;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

/**
 * This class is the implementation class of the {@link ProcessingService}
 * interface.
 * 
 * @author Tobias Schlecht
 */
public final class ProcessingServiceImpl extends AbstractService implements ProcessingService
{
	private final CoreService coreService;

	private Client restletClient;

	/**
	 * The constructor is {@code private} to have strict control what instances
	 * exist at any time. Instead of the constructor the {@code public}
	 * <i>static factory method</i> {@link #getInstance(URL, JavaClient)}
	 * returns the instances of the class.
	 * 
	 * @param serviceURL The service {@code URL}
	 * @param javaClient The corresponding {@code JavaClient}
	 */
	private ProcessingServiceImpl(URL serviceURL, JavaClient javaClient)
	{
		super(ProcessingServiceID.INSTANCE, serviceURL, javaClient);

		coreService = javaClient.getCoreService();

		initializeRestletClient();
	}

	/**
	 * Returns a {@link ProcessingService} instance for the specified service
	 * {@link URL} and the corresponding {@link JavaClient}.
	 * 
	 * @param serviceURL The service {@code URL}
	 * @param javaClient The corresponding {@code JavaClient}
	 * @return A {@code ProcessingService} instance
	 */
	@InvocationLimited(legitimateInvocationClasses = {JavaClientImpl.class})
	public static synchronized ProcessingService getInstance(URL serviceURL, JavaClient javaClient)
	{
		serviceURL = URLTransformationUtils.adjustServiceURL(serviceURL);

		String warningMessage = "The method invocation \"" + ProcessingServiceImpl.class.getCanonicalName() + ".getInstance(URL, JavaClient)\" is not intended for this usage. Use the method \"" + JavaClient.class.getCanonicalName() + ".getService(ServicePluginID)\" instead.";
		InvocationUtils.checkMethodInvocation(warningMessage, "getInstance", URL.class, JavaClient.class);

		return new ProcessingServiceImpl(serviceURL, javaClient);
	}

	@Override
	public URL getThumbnailURL(DocumentID documentID, int size)
	{
		return getThumbnailURL(documentID, size, true);
	}

	@Override
	public URL getThumbnailURL(DocumentID documentID, int size, boolean immediate)
	{
		if(documentID == null)
		{
			throw new NullPointerException();
		}

		Document document = coreService.getDocument(documentID);

		if(size < 16 || size > 256 || document == null)
		{
			throw new IllegalArgumentException();
		}

		return getThumbnailURL(documentID.getUniqueResourceURL(), size, immediate);
	}

	@Override
	public URL getThumbnailURL(AttachmentID attachmentID, int size)
	{
		return getThumbnailURL(attachmentID, size, true);
	}

	@Override
	public URL getThumbnailURL(AttachmentID attachmentID, int size, boolean immediate)
	{
		if(attachmentID == null)
		{
			throw new NullPointerException();
		}

		Document document = coreService.getDocument((DocumentID) attachmentID.getRootID(), LoadType.SNAPSHOT, false);

		if(document == null)
		{
			throw new IllegalArgumentException();
		}

		Attachment attachment = document.getAttachment(attachmentID);

		if(size < 16 || size > 256 || attachment == null)
		{
			throw new IllegalArgumentException();
		}

		return getThumbnailURL(attachmentID.getUniqueResourceURL(), size, immediate);
	}

	/**
	 * Returns the {@link URL} of the thumbnail with the specified data.
	 * 
	 * @param uniqueResourceURL A unique resource {@code URL} of a
	 *        {@code DocumentID} or of an {@code AttachmentID}
	 * @param size The size of the (quadratic) thumbnail in pixels ({@code 16 <=
	 *        size <= 256})
	 * @param immediate {@code true} if a created {@code ProcessingService}
	 *        {@code URL} should be returned immediately; {@code false} if the
	 *        real thumbnail {@code URL} should be returned as soon as possible
	 * @return The {@code URL} of the thumbnail
	 */
	private URL getThumbnailURL(URL uniqueResourceURL, int size, boolean immediate)
	{
		String encodedUniqueResourceURLString = "";

		try
		{
			encodedUniqueResourceURLString = URLEncoder.encode(uniqueResourceURL.toExternalForm(), "UTF-8");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		String urlString = serviceURL.toExternalForm() + "/process/generic/thumbnail?item=" + encodedUniqueResourceURLString + "&async=false&size=" + size;

		return getResultURL(urlString, immediate);
	}

	@Override
	public URL getImageURL(DocumentID documentID, int size, Fixed fixed)
	{
		return getImageURL(documentID, size, fixed, true);
	}

	@Override
	public URL getImageURL(DocumentID documentID, int size, Fixed fixed, boolean immediate)
	{
		if(documentID == null || fixed == null)
		{
			throw new NullPointerException();
		}

		Document document = coreService.getDocument(documentID);

		if(size <= 0 || document == null || !document.getMediaMainType().equals(MediaTypeFactory.getMediaMainType("image")))
		{
			throw new IllegalArgumentException();
		}

		return getImageURL(documentID.getUniqueResourceURL(), size, fixed, immediate);
	}

	@Override
	public URL getImageURL(AttachmentID attachmentID, int size, Fixed fixed)
	{
		return getImageURL(attachmentID, size, fixed, true);
	}

	@Override
	public URL getImageURL(AttachmentID attachmentID, int size, Fixed fixed, boolean immediate)
	{
		if(attachmentID == null || fixed == null)
		{
			throw new NullPointerException();
		}

		Document document = coreService.getDocument((DocumentID) attachmentID.getRootID(), LoadType.SNAPSHOT, false);

		if(document == null)
		{
			throw new IllegalArgumentException();
		}

		Attachment attachment = document.getAttachment(attachmentID);

		if(size <= 0 || attachment == null || !attachment.getMediaType().getMediaMainType().equals(MediaTypeFactory.getMediaMainType("image")))
		{
			throw new IllegalArgumentException();
		}

		return getImageURL(attachmentID.getUniqueResourceURL(), size, fixed, immediate);
	}

	/**
	 * Returns the {@link URL} of the image with the specified data.
	 * 
	 * @param uniqueResourceURL A unique resource {@code URL} of a
	 *        {@code DocumentID} or of an {@code AttachmentID}
	 * @param size The width or the height of the image in pixels
	 * @param fixed {@code Fixed.WIDTH} if the {@code size} value specifies the
	 *        width of the image; {@code Fixed.HEIGHT} if the {@code size} value
	 *        specifies the height of the image
	 * @param immediate {@code true} if a created {@code ProcessingService}
	 *        {@code URL} should be returned immediately; {@code false} if the
	 *        real image {@code URL} should be returned as soon as possible
	 * @return The {@code URL} of the image
	 */
	private URL getImageURL(URL uniqueResourceURL, int size, Fixed fixed, boolean immediate)
	{
		String encodedUniqueResourceURLString = "";

		try
		{
			encodedUniqueResourceURLString = URLEncoder.encode(uniqueResourceURL.toExternalForm(), "UTF-8");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		String resolution = getAPIResolutionString(size, fixed);

		String urlString = serviceURL.toExternalForm() + "/process/image/resized?item=" + encodedUniqueResourceURLString + "&async=false&resolution=" + resolution;

		return getResultURL(urlString, immediate);
	}

	@Override
	public URL getVideoSnapshotURL(DocumentID documentID, int size, Fixed fixed)
	{
		return getVideoSnapshotURL(documentID, size, fixed, 0.5f, true);
	}

	@Override
	public URL getVideoSnapshotURL(DocumentID documentID, int size, Fixed fixed, float time, boolean immediate)
	{
		if(documentID == null || fixed == null)
		{
			throw new NullPointerException();
		}

		Document document = coreService.getDocument(documentID);

		if(size <= 0 || time < 0.0f || time > 1.0f || document == null || !document.getMediaMainType().equals(MediaTypeFactory.getMediaMainType("video")))
		{
			throw new IllegalArgumentException();
		}

		return getVideoSnapshotURL(documentID.getUniqueResourceURL(), size, fixed, time, immediate);
	}

	@Override
	public URL getVideoSnapshotURL(AttachmentID attachmentID, int size, Fixed fixed)
	{
		return getVideoSnapshotURL(attachmentID, size, fixed, 0.5f, true);
	}

	@Override
	public URL getVideoSnapshotURL(AttachmentID attachmentID, int size, Fixed fixed, float time, boolean immediate)
	{
		if(attachmentID == null || fixed == null)
		{
			throw new NullPointerException();
		}

		Document document = coreService.getDocument((DocumentID) attachmentID.getRootID(), LoadType.SNAPSHOT, false);

		if(document == null)
		{
			throw new IllegalArgumentException();
		}

		Attachment attachment = document.getAttachment(attachmentID);

		if(size <= 0 || time < 0.0f || time > 1.0f || attachment == null || !attachment.getMediaType().getMediaMainType().equals(MediaTypeFactory.getMediaMainType("video")))
		{
			throw new IllegalArgumentException();
		}

		return getVideoSnapshotURL(attachmentID.getUniqueResourceURL(), size, fixed, time, immediate);
	}

	/**
	 * Returns the {@link URL} of the video snapshot with the specified data.
	 * 
	 * @param uniqueResourceURL A unique resource {@code URL} of a
	 *        {@code DocumentID} or of an {@code AttachmentID}
	 * @param size The width or the height of the snapshot in pixels
	 * @param fixed {@code Fixed.WIDTH} if the {@code size} value specifies the
	 *        width of the snapshot; {@code Fixed.HEIGHT} if the {@code size}
	 *        value specifies the height of the snapshot
	 * @param time This value determines the time of extraction of the snapshot
	 *        ({@code 0 <= time <= 1 | 0.5 = the middle of the video})
	 * @param immediate {@code true} if a created {@code ProcessingService}
	 *        {@code URL} should be returned immediately; {@code false} if the
	 *        real video snapshot {@code URL} should be returned as soon as
	 *        possible
	 * @return The {@code URL} of the video snapshot
	 */
	private URL getVideoSnapshotURL(URL uniqueResourceURL, int size, Fixed fixed, float time, boolean immediate)
	{
		String encodedUniqueResourceURLString = "";

		try
		{
			encodedUniqueResourceURLString = URLEncoder.encode(uniqueResourceURL.toExternalForm(), "UTF-8");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		String resolution = getAPIResolutionString(size, fixed);

		String urlString = serviceURL.toExternalForm() + "/process/video/still?item=" + encodedUniqueResourceURLString + "&async=false&resolution=" + resolution + "&timecode=" + time;

		return getResultURL(urlString, immediate);
	}

	/**
	 * Returns the {@link URL} of the resource with the specified data.
	 * 
	 * @param urlString The {@code URL} ({@code String} representation) for the
	 *        HTTP GET request
	 * @param immediate {@code true} if a created {@code ProcessingService}
	 *        {@code URL} should be returned immediately; {@code false} if the
	 *        real resource {@code URL} should be returned as soon as possible
	 * @return The {@code URL} of the resource
	 */
	private URL getResultURL(String urlString, boolean immediate)
	{
		if(immediate)
		{
			try
			{
				return new URL(urlString);
			}
			catch(MalformedURLException e)
			{
				e.printStackTrace();

				return null;
			}
		}
		else
		{
			ClientResource clientResource = new ClientResource(urlString);

			clientResource.setNext(restletClient);

			try
			{
				clientResource.get();

				System.out.println("[ProcessingService ProcessingServiceImpl] " + urlString);
			}
			catch(ResourceException e)
			{
				System.err.println("[ProcessingService ProcessingServiceImpl] " + e.getStatus().getCode());

				return null;
			}

			if(clientResource.getResponse().getStatus().getCode() == 303)
			{
				return clientResource.getResponse().getLocationRef().toUrl();
			}
			else
			{
				System.err.println("[ProcessingService ProcessingServiceImpl] " + clientResource.getResponse().getStatus().getCode());

				return null;
			}
		}
	}

	/**
	 * Returns the resolution {@code String} according to the API format.
	 * 
	 * @param size The width or the height of the resource in pixels
	 * @param fixed {@code Fixed.WIDTH} if the {@code size} value specifies the
	 *        width of the resource; {@code Fixed.HEIGHT} if the {@code size}
	 *        value specifies the height of the resource
	 * @return The API resolution {@code String}
	 */
	private String getAPIResolutionString(int size, Fixed fixed)
	{
		if(fixed == Fixed.WIDTH)
		{
			return size + "xY";
		}
		else
		{
			return "Xx" + size;
		}
	}

	/**
	 * Initializes the <i>Restlet</i> {@link Client} of this
	 * {@link ProcessingService}.
	 */
	private void initializeRestletClient()
	{
		XMLConfiguration xmlConfiguration = ProcessingServiceID.INSTANCE.getXMLConfiguration();

		Context restletContext = new Context();

		String[] names = xmlConfiguration.getStringArray("restlet-client/connector-parameters/parameter/@name");
		String[] values = xmlConfiguration.getStringArray("restlet-client/connector-parameters/parameter/@value");

		for(int i = 0; i < names.length; i++)
		{
			restletContext.getParameters().add(names[i], values[i]);
		}

		restletClient = new Client(restletContext, Protocol.valueOf(xmlConfiguration.getString("restlet-client/connector-protocol")));

		List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();
		converters.clear();
		converters.add(new JacksonConverter());

		try
		{
			restletClient.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
