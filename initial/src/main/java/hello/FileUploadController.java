package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by hklv on 2015/11/3.
 */
@Controller
public class FileUploadController {
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public
    @ResponseBody
    String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    String handleFileUpload(@RequestParam("name") String name,
                            @RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        int index = fileName.indexOf(".");
        //文件类型
        String fileType = fileName.substring(index);
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                //文件的存放路径
                String path = "d:\\test\\";
                String fileFullPath = path + name + fileType;
                File newFile = new File(fileFullPath);
                if (!newFile.getParentFile().exists()) {
                    newFile.getParentFile().mkdirs();
                }
                newFile.createNewFile();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(newFile));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
}
