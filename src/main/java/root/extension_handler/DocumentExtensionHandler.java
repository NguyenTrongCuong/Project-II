package root.extension_handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("documentExtensionHandler")
public class DocumentExtensionHandler implements ExtensionHandler {
	private final List<String> documentExtensions = Lists.newArrayList("doc", "docx", "txt", "pptx", "pdf", "xls", "xlsx", "ppt");
	
	@Override
	public boolean match(String extension) {
		return this.documentExtensions.contains(extension);
	}

}
