package root.extension_handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("imageExtensionHandler")
public class ImageExtensionHandler implements ExtensionHandler {
	private final List<String> imageExtensions = Lists.newArrayList("png", "jpg", "jfif", "jpeg");
	
	@Override
	public boolean match(String extension) {
		return imageExtensions.contains(extension);
	}

}
