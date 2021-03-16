package root.aws_s3_service;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class AWSS3Service {
	@Autowired
	private Environment env;
	
	@Autowired
	private AmazonS3 s3Client;
	
	public String uploadFile(MultipartFile multipartFile) {
		String fileName = multipartFile.getOriginalFilename();
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentDisposition("attachment");
		metadata.setContentLength(multipartFile.getSize());
		
		try {
			this.s3Client.putObject(this.env.getProperty("aws.bucket.name"), fileName, multipartFile.getInputStream(), metadata);
		} 
		catch (AmazonServiceException e) {
			e.printStackTrace();
		} 
		catch (SdkClientException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		String objectLink = "https://" + this.env.getProperty("aws.bucket.name") + ".s3-" + this.env.getProperty("cloud.aws.region.static") + ".amazonaws.com/" + fileName;
		
		return objectLink;
	}
	
//	private File convertMultipartFileToFile(MultipartFile multipartFile) {
//		File convertedFile = new File(multipartFile.getOriginalFilename());
//		try(FileOutputStream fos = new FileOutputStream(convertedFile)) {
//			fos.write(multipartFile.getBytes());
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		return convertedFile;
//	}

}


















































