package root.extension_handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("videoExtensionHandler")
public class VideoExtensionHandler implements ExtensionHandler {
	private final List<String> videoExtensions = Lists.newArrayList("mp4");

	@Override
	public boolean match(String extension) {
		return this.videoExtensions.contains(extension);
	}

}
