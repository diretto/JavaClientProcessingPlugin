package org.diretto.api.client.external.processing;

import java.net.URL;

import org.diretto.api.client.main.core.entities.AttachmentID;
import org.diretto.api.client.main.core.entities.DocumentID;
import org.diretto.api.client.service.Service;

/**
 * This interface represents a {@code ProcessingService}. <br/><br/>
 * 
 * The {@code ProcessingService} provides the bulk of the platform
 * functionalities in respect of the {@code Processing API}.
 * 
 * @author Tobias Schlecht
 */
public interface ProcessingService extends Service
{
	/**
	 * Determines whether the width or the height will be the fixed size of the
	 * corresponding new resource. The other size value will be calculated in
	 * proportion to the original resource ratio.
	 */
	public enum Fixed
	{
		/**
		 * The fixed size of the corresponding new resource will be the width.
		 * The height will be calculated in proportion to the original resource
		 * ratio.
		 */
		WIDTH,

		/**
		 * The fixed size of the corresponding new resource will be the height.
		 * The width will be calculated in proportion to the original resource
		 * ratio.
		 */
		HEIGHT;
	}

	/**
	 * Returns the {@link URL} of the thumbnail with the specified data.
	 * <br/><br/>
	 * 
	 * <i>Annotation:</i> Thumbnails are always quadratic. <br/><br/>
	 * 
	 * <i>Important:</i> The requested {@code URL} is returned immediately but
	 * it is a created {@link ProcessingService} {@code URL} which will redirect
	 * to the real resource {@code URL} ({@code 303 See Other}). If the given
	 * data are invalid (e.g. if the specified resolution is higher than the
	 * original one), the request will fail ({@code 400 Bad Request} or
	 * {@code 404 Not Found}).
	 * 
	 * @param documentID A {@code DocumentID}
	 * @param size The size of the (quadratic) thumbnail in pixels ({@code 16 <=
	 *        size <= 256})
	 * @return The {@code URL} of the thumbnail
	 */
	URL getThumbnailURL(DocumentID documentID, int size);

	/**
	 * Returns the {@link URL} of the thumbnail with the specified data.
	 * <br/><br/>
	 * 
	 * <i>Annotation:</i> Thumbnails are always quadratic. <br/><br/>
	 * 
	 * <i>Important:</i> The requested {@code URL} is returned immediately but
	 * it is a created {@link ProcessingService} {@code URL} which will redirect
	 * to the real resource {@code URL} ({@code 303 See Other}). If the given
	 * data are invalid (e.g. if the specified resolution is higher than the
	 * original one), the request will fail ({@code 400 Bad Request} or
	 * {@code 404 Not Found}).
	 * 
	 * @param attachmentID An {@code AttachmentID}
	 * @param size The size of the (quadratic) thumbnail in pixels ({@code 16 <=
	 *        size <= 256})
	 * @return The {@code URL} of the thumbnail
	 */
	URL getThumbnailURL(AttachmentID attachmentID, int size);

	/**
	 * Returns the {@link URL} of the thumbnail with the specified data.
	 * <br/><br/>
	 * 
	 * <i>Annotation:</i> Thumbnails are always quadratic. <br/><br/>
	 * 
	 * <i>Important:</i> <br/><br/>
	 * 
	 * {@code immediate = true} <br/><br/>
	 * 
	 * The requested {@code URL} is returned immediately but it is a created
	 * {@link ProcessingService} {@code URL} which will redirect to the real
	 * resource {@code URL} ({@code 303 See Other}). If the given data are
	 * invalid (e.g. if the specified resolution is higher than the original
	 * one), the request will fail ({@code 400 Bad Request} or
	 * {@code 404 Not Found}). <br/><br/>
	 * 
	 * {@code immediate = false} <br/><br/>
	 * 
	 * The returned {@code URL} is the real resource {@code URL} or if the given
	 * data are invalid (e.g. if the specified resolution is higher than the
	 * original one), the returned {@code URL} will be {@code null} and
	 * therefore the error can be handled before the request is executed. But
	 * note that it can take some time until the {@code URL} will be returned.
	 * 
	 * @param documentID A {@code DocumentID}
	 * @param size The size of the (quadratic) thumbnail in pixels ({@code 16 <=
	 *        size <= 256})
	 * @param immediate {@code true} if a created {@code ProcessingService}
	 *        {@code URL} should be returned immediately; {@code false} if the
	 *        real thumbnail {@code URL} should be returned as soon as possible
	 * @return The {@code URL} of the thumbnail
	 */
	URL getThumbnailURL(DocumentID documentID, int size, boolean immediate);

	/**
	 * Returns the {@link URL} of the thumbnail with the specified data.
	 * <br/><br/>
	 * 
	 * <i>Annotation:</i> Thumbnails are always quadratic. <br/><br/>
	 * 
	 * <i>Important:</i> <br/><br/>
	 * 
	 * {@code immediate = true} <br/><br/>
	 * 
	 * The requested {@code URL} is returned immediately but it is a created
	 * {@link ProcessingService} {@code URL} which will redirect to the real
	 * resource {@code URL} ({@code 303 See Other}). If the given data are
	 * invalid (e.g. if the specified resolution is higher than the original
	 * one), the request will fail ({@code 400 Bad Request} or
	 * {@code 404 Not Found}). <br/><br/>
	 * 
	 * {@code immediate = false} <br/><br/>
	 * 
	 * The returned {@code URL} is the real resource {@code URL} or if the given
	 * data are invalid (e.g. if the specified resolution is higher than the
	 * original one), the returned {@code URL} will be {@code null} and
	 * therefore the error can be handled before the request is executed. But
	 * note that it can take some time until the {@code URL} will be returned.
	 * 
	 * @param attachmentID An {@code AttachmentID}
	 * @param size The size of the (quadratic) thumbnail in pixels ({@code 16 <=
	 *        size <= 256})
	 * @param immediate {@code true} if a created {@code ProcessingService}
	 *        {@code URL} should be returned immediately; {@code false} if the
	 *        real thumbnail {@code URL} should be returned as soon as possible
	 * @return The {@code URL} of the thumbnail
	 */
	URL getThumbnailURL(AttachmentID attachmentID, int size, boolean immediate);

	/**
	 * Returns the {@link URL} of the image with the specified data. <br/><br/>
	 * 
	 * <i>Annotation:</i> The ratio of the new image will be the same as the
	 * ratio of the original image. <br/><br/>
	 * 
	 * <i>Important:</i> The requested {@code URL} is returned immediately but
	 * it is a created {@link ProcessingService} {@code URL} which will redirect
	 * to the real resource {@code URL} ({@code 303 See Other}). If the given
	 * data are invalid (e.g. if the specified resolution is higher than the
	 * original one), the request will fail ({@code 400 Bad Request} or
	 * {@code 404 Not Found}).
	 * 
	 * @param documentID A {@code DocumentID}
	 * @param size The width or the height of the image in pixels
	 * @param fixed {@code Fixed.WIDTH} if the {@code size} value specifies the
	 *        width of the image; {@code Fixed.HEIGHT} if the {@code size} value
	 *        specifies the height of the image
	 * @return The {@code URL} of the image
	 */
	URL getImageURL(DocumentID documentID, int size, Fixed fixed);

	/**
	 * Returns the {@link URL} of the image with the specified data. <br/><br/>
	 * 
	 * <i>Annotation:</i> The ratio of the new image will be the same as the
	 * ratio of the original image. <br/><br/>
	 * 
	 * <i>Important:</i> The requested {@code URL} is returned immediately but
	 * it is a created {@link ProcessingService} {@code URL} which will redirect
	 * to the real resource {@code URL} ({@code 303 See Other}). If the given
	 * data are invalid (e.g. if the specified resolution is higher than the
	 * original one), the request will fail ({@code 400 Bad Request} or
	 * {@code 404 Not Found}).
	 * 
	 * @param attachmentID An {@code AttachmentID}
	 * @param size The width or the height of the image in pixels
	 * @param fixed {@code Fixed.WIDTH} if the {@code size} value specifies the
	 *        width of the image; {@code Fixed.HEIGHT} if the {@code size} value
	 *        specifies the height of the image
	 * @return The {@code URL} of the image
	 */
	URL getImageURL(AttachmentID attachmentID, int size, Fixed fixed);

	/**
	 * Returns the {@link URL} of the image with the specified data. <br/><br/>
	 * 
	 * <i>Annotation:</i> The ratio of the new image will be the same as the
	 * ratio of the original image. <br/><br/>
	 * 
	 * <i>Important:</i> <br/><br/>
	 * 
	 * {@code immediate = true} <br/><br/>
	 * 
	 * The requested {@code URL} is returned immediately but it is a created
	 * {@link ProcessingService} {@code URL} which will redirect to the real
	 * resource {@code URL} ({@code 303 See Other}). If the given data are
	 * invalid (e.g. if the specified resolution is higher than the original
	 * one), the request will fail ({@code 400 Bad Request} or
	 * {@code 404 Not Found}). <br/><br/>
	 * 
	 * {@code immediate = false} <br/><br/>
	 * 
	 * The returned {@code URL} is the real resource {@code URL} or if the given
	 * data are invalid (e.g. if the specified resolution is higher than the
	 * original one), the returned {@code URL} will be {@code null} and
	 * therefore the error can be handled before the request is executed. But
	 * note that it can take some time until the {@code URL} will be returned.
	 * 
	 * @param documentID A {@code DocumentID}
	 * @param size The width or the height of the image in pixels
	 * @param fixed {@code Fixed.WIDTH} if the {@code size} value specifies the
	 *        width of the image; {@code Fixed.HEIGHT} if the {@code size} value
	 *        specifies the height of the image
	 * @param immediate {@code true} if a created {@code ProcessingService}
	 *        {@code URL} should be returned immediately; {@code false} if the
	 *        real image {@code URL} should be returned as soon as possible
	 * @return The {@code URL} of the image
	 */
	URL getImageURL(DocumentID documentID, int size, Fixed fixed, boolean immediate);

	/**
	 * Returns the {@link URL} of the image with the specified data. <br/><br/>
	 * 
	 * <i>Annotation:</i> The ratio of the new image will be the same as the
	 * ratio of the original image. <br/><br/>
	 * 
	 * <i>Important:</i> <br/><br/>
	 * 
	 * {@code immediate = true} <br/><br/>
	 * 
	 * The requested {@code URL} is returned immediately but it is a created
	 * {@link ProcessingService} {@code URL} which will redirect to the real
	 * resource {@code URL} ({@code 303 See Other}). If the given data are
	 * invalid (e.g. if the specified resolution is higher than the original
	 * one), the request will fail ({@code 400 Bad Request} or
	 * {@code 404 Not Found}). <br/><br/>
	 * 
	 * {@code immediate = false} <br/><br/>
	 * 
	 * The returned {@code URL} is the real resource {@code URL} or if the given
	 * data are invalid (e.g. if the specified resolution is higher than the
	 * original one), the returned {@code URL} will be {@code null} and
	 * therefore the error can be handled before the request is executed. But
	 * note that it can take some time until the {@code URL} will be returned.
	 * 
	 * @param attachmentID An {@code AttachmentID}
	 * @param size The width or the height of the image in pixels
	 * @param fixed {@code Fixed.WIDTH} if the {@code size} value specifies the
	 *        width of the image; {@code Fixed.HEIGHT} if the {@code size} value
	 *        specifies the height of the image
	 * @param immediate {@code true} if a created {@code ProcessingService}
	 *        {@code URL} should be returned immediately; {@code false} if the
	 *        real image {@code URL} should be returned as soon as possible
	 * @return The {@code URL} of the image
	 */
	URL getImageURL(AttachmentID attachmentID, int size, Fixed fixed, boolean immediate);

	/**
	 * Returns the {@link URL} of the video snapshot with the specified data.
	 * <br/><br/>
	 * 
	 * <i>Annotation:</i> The ratio of the snapshot will be the same as the
	 * ratio of the video. Furthermore the requested {@code URL} belongs to a
	 * snapshot which is taken from the middle of the video. <br/><br/>
	 * 
	 * <i>Important:</i> The requested {@code URL} is returned immediately but
	 * it is a created {@link ProcessingService} {@code URL} which will redirect
	 * to the real resource {@code URL} ({@code 303 See Other}). If the given
	 * data are invalid (e.g. if the specified resolution is higher than the
	 * original one), the request will fail ({@code 400 Bad Request} or
	 * {@code 404 Not Found}).
	 * 
	 * @param documentID A {@code DocumentID}
	 * @param size The width or the height of the snapshot in pixels
	 * @param fixed {@code Fixed.WIDTH} if the {@code size} value specifies the
	 *        width of the snapshot; {@code Fixed.HEIGHT} if the {@code size}
	 *        value specifies the height of the snapshot
	 * @return The {@code URL} of the video snapshot
	 */
	URL getVideoSnapshotURL(DocumentID documentID, int size, Fixed fixed);

	/**
	 * Returns the {@link URL} of the video snapshot with the specified data.
	 * <br/><br/>
	 * 
	 * <i>Annotation:</i> The ratio of the snapshot will be the same as the
	 * ratio of the video. Furthermore the requested {@code URL} belongs to a
	 * snapshot which is taken from the middle of the video. <br/><br/>
	 * 
	 * <i>Important:</i> The requested {@code URL} is returned immediately but
	 * it is a created {@link ProcessingService} {@code URL} which will redirect
	 * to the real resource {@code URL} ({@code 303 See Other}). If the given
	 * data are invalid (e.g. if the specified resolution is higher than the
	 * original one), the request will fail ({@code 400 Bad Request} or
	 * {@code 404 Not Found}).
	 * 
	 * @param attachmentID An {@code AttachmentID}
	 * @param size The width or the height of the snapshot in pixels
	 * @param fixed {@code Fixed.WIDTH} if the {@code size} value specifies the
	 *        width of the snapshot; {@code Fixed.HEIGHT} if the {@code size}
	 *        value specifies the height of the snapshot
	 * @return The {@code URL} of the video snapshot
	 */
	URL getVideoSnapshotURL(AttachmentID attachmentID, int size, Fixed fixed);

	/**
	 * Returns the {@link URL} of the video snapshot with the specified data.
	 * <br/><br/>
	 * 
	 * <i>Annotation:</i> The ratio of the snapshot will be the same as the
	 * ratio of the video. <br/><br/>
	 * 
	 * <i>Important:</i> <br/><br/>
	 * 
	 * {@code immediate = true} <br/><br/>
	 * 
	 * The requested {@code URL} is returned immediately but it is a created
	 * {@link ProcessingService} {@code URL} which will redirect to the real
	 * resource {@code URL} ({@code 303 See Other}). If the given data are
	 * invalid (e.g. if the specified resolution is higher than the original
	 * one), the request will fail ({@code 400 Bad Request} or
	 * {@code 404 Not Found}). <br/><br/>
	 * 
	 * {@code immediate = false} <br/><br/>
	 * 
	 * The returned {@code URL} is the real resource {@code URL} or if the given
	 * data are invalid (e.g. if the specified resolution is higher than the
	 * original one), the returned {@code URL} will be {@code null} and
	 * therefore the error can be handled before the request is executed. But
	 * note that it can take some time until the {@code URL} will be returned.
	 * 
	 * @param documentID A {@code DocumentID}
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
	URL getVideoSnapshotURL(DocumentID documentID, int size, Fixed fixed, float time, boolean immediate);

	/**
	 * Returns the {@link URL} of the video snapshot with the specified data.
	 * <br/><br/>
	 * 
	 * <i>Annotation:</i> The ratio of the snapshot will be the same as the
	 * ratio of the video. <br/><br/>
	 * 
	 * <i>Important:</i> <br/><br/>
	 * 
	 * {@code immediate = true} <br/><br/>
	 * 
	 * The requested {@code URL} is returned immediately but it is a created
	 * {@link ProcessingService} {@code URL} which will redirect to the real
	 * resource {@code URL} ({@code 303 See Other}). If the given data are
	 * invalid (e.g. if the specified resolution is higher than the original
	 * one), the request will fail ({@code 400 Bad Request} or
	 * {@code 404 Not Found}). <br/><br/>
	 * 
	 * {@code immediate = false} <br/><br/>
	 * 
	 * The returned {@code URL} is the real resource {@code URL} or if the given
	 * data are invalid (e.g. if the specified resolution is higher than the
	 * original one), the returned {@code URL} will be {@code null} and
	 * therefore the error can be handled before the request is executed. But
	 * note that it can take some time until the {@code URL} will be returned.
	 * 
	 * @param attachmentID An {@code AttachmentID}
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
	URL getVideoSnapshotURL(AttachmentID attachmentID, int size, Fixed fixed, float time, boolean immediate);
}
