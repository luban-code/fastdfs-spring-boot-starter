package com.cicoding.fastdfs.util;

import com.cicoding.fastdfs.common.FileBasicInfo;
import com.cicoding.fastdfs.config.FastdfsConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * author: zhaokejin
 */
@Component
public class FastdfsUtil {

    private static Logger logger = LogManager.getLogger(FastdfsUtil.class);
    @Autowired
    private FastdfsConfig config;

    /**
     * 上传文件
     * 基于MultipartFile的getByte()将文件存储到Fastdfs
     * @param fileByte 文件内容byte
     * @param fileName 文件名,如果有类型后缀，则需要包含，例如chrome.exe
     * @return fastdfs的fileId
     * @throws IOException
     * @throws MyException
     */
    public String uploadFile(byte[] fileByte, String fileName) throws IOException, MyException {
        return uploadFile(fileByte, fileName, null, null);
    }


    /**
     * 上传文件
     * 基于MultipartFile的getByte()将文件存储到Fastdfs
     * @param fileByte 文件内容byte
     * @param fileName 文件名,如果有类型后缀，则需要包含，例如chrome.exe
     * @param metaInfos 附加信息，注：会自动将fileName以OriginFileName为key存储到metaInfo中
     * @return fastdfs的fileId
     * @throws IOException
     * @throws MyException
     */
    public String uploadFile(byte[] fileByte, String fileName, Map<String, String> metaInfos) throws IOException, MyException {
        return uploadFile(fileByte, fileName, null, metaInfos);
    }

    /**
     * 上传文件
     * 基于MultipartFile的getByte()将文件存储到Fastdfs
     * @param fileByte 文件内容byte
     * @param fileName 文件名
     * @param extName 后缀名，如果为空则从fileName中获取最后一个.后面的
     * @param metaInfos 附加信息，注：会自动将fileName以OriginFileName为key、extName以OriginFileKeyName为key存储到metaInfo中
     * @return fastdfs的fileId
     * @throws IOException
     * @throws MyException
     */
    private String uploadFile(byte[] fileByte, String fileName, String extName, Map<String, String> metaInfos) throws IOException, MyException {
        if (extName == null) {
            int nPos = fileName.lastIndexOf(46);
            if (nPos > 0 && fileName.length() - nPos <= 7) {
                extName = fileName.substring(nPos + 1);
            }
        }
        NameValuePair nvp [] = getMetaInfos(fileName, extName, metaInfos);
        ClientGlobal.init(config.getConfigFile());
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;

        StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
        String fileIds = storageClient.upload_file1(fileByte, extName, nvp);
        return fileIds;
    }

    /**
     * 将文件上传到指定的group。
     * 基于MultipartFile的getByte()将文件存储到Fastdfs
     * @param groupName groupName
     * @param fileByte 文件内容byte
     * @param fileName 文件名,如果有类型后缀，则需要包含，例如chrome.exe
     * @return fastdfs的fileId
     * @throws IOException
     * @throws MyException
     */
    public String uploadFile2Group(String groupName, byte[] fileByte, String fileName) throws IOException, MyException {
        return uploadFile2Group(groupName, fileByte, fileName, null, null);
    }


    /**
     * 将文件上传到指定的group。
     * 基于MultipartFile的getByte()将文件存储到Fastdfs
     * @param groupName groupName
     * @param fileByte 文件内容byte
     * @param fileName 文件名,如果有类型后缀，则需要包含，例如chrome.exe
     * @param metaInfos 附加信息，注：会自动将fileName以OriginFileName为key存储到metaInfo中
     * @return fastdfs的fileId
     * @throws IOException
     * @throws MyException
     */
    public String uploadFile2Group(String groupName, byte[] fileByte, String fileName, Map<String, String> metaInfos) throws IOException, MyException {
        return uploadFile2Group(groupName, fileByte, fileName, null, metaInfos);
    }

    /**
     * 将文件上传到指定的group。
     * 基于MultipartFile的getByte()将文件存储到Fastdfs
     * @param groupName groupName
     * @param fileByte 文件内容byte
     * @param fileName 文件名
     * @param extName 后缀名，如果为空则从fileName中获取最后一个.后面的
     * @param metaInfos 附加信息，注：会自动将fileName以OriginFileName为key、extName以OriginFileKeyName为key存储到metaInfo中
     * @return fastdfs的fileId
     * @throws IOException
     * @throws MyException
     */
    private String uploadFile2Group(String groupName, byte[] fileByte, String fileName, String extName, Map<String, String> metaInfos) throws IOException, MyException {
        if (extName == null) {
            int nPos = fileName.lastIndexOf(46);
            if (nPos > 0 && fileName.length() - nPos <= 7) {
                extName = fileName.substring(nPos + 1);
            }
        }
        NameValuePair nvp [] = getMetaInfos(fileName, extName, metaInfos);
        ClientGlobal.init(config.getConfigFile());
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;

        StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
        String fileIds = storageClient.upload_file1(groupName, fileByte, extName, nvp);
        return fileIds;
    }

    /**
     * 构造NameValuePair[]
     * 会自动将fileName以OriginFileName为key、extName以OriginFileKeyName为key存储到metaInfo中
     * @param fileName
     * @param extName
     * @param metaInfos
     * @return fastdfs的fileId
     */
    private NameValuePair[] getMetaInfos(String fileName, String extName, Map<String, String> metaInfos){
        int nvpSize = 2;
        if(metaInfos != null){
            nvpSize += metaInfos.size();
        }
        NameValuePair nvp [] = new NameValuePair[nvpSize];
        nvp[0] = new NameValuePair("OriginFileName");
        nvp[0].setValue(fileName);

        nvp[1] = new NameValuePair("OriginFileExtName");
        nvp[1].setValue(extName);
        if(metaInfos != null) {
            Iterator<String> metaKeys = metaInfos.keySet().iterator();
            int index = 2;
            String key, value;
            while (metaKeys.hasNext()) {
                key = metaKeys.next();
                value = metaInfos.get(key);
                nvp[index] = new NameValuePair(key);
                nvp[index].setValue(value);
                index++;
            }
        }
        return nvp;
    }

    /**
     * 基于fastdfs的fileId下载文件
     * @param fileId
     * @return 返回文件的byte[]
     * @throws IOException
     * @throws MyException
     */
    public byte[] downLoadFile(String fileId) throws IOException, MyException {
        ClientGlobal.init(config.getConfigFile());

        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;

        StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
        byte[] fileBytes = storageClient.download_file1(fileId);
        return fileBytes;
    }

    /**
     * 基于fastdfs的fileId删除文件
     * @param fileId
     * @return
     * @throws IOException
     * @throws MyException
     */
    public boolean deleteFile(String fileId) throws IOException, MyException {
        ClientGlobal.init(config.getConfigFile());

        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;

        StorageClient1 storageClient = new StorageClient1(trackerServer,
                storageServer);
        int i = storageClient.delete_file1(fileId);
        if(logger.isDebugEnabled()) {
            logger.debug(i == 0 ? "删除成功" : "删除失败:" + i);
        }
        return i == 0;
    }

    /**
     * 根据fileId获取文件基本信息，包括sourceIpAddr、fileSize（单位是byte）、createTime
     * @param fileId
     * @return FileBasicInfo包括sourceIpAddr、fileSize（单位是byte）、createTime
     * @throws IOException
     * @throws MyException
     */
    public FileBasicInfo getFileBasicInfo(String fileId) throws IOException, MyException {
        ClientGlobal.init(config.getConfigFile());

        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;

        StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
        FileInfo fi = storageClient.get_file_info1(fileId);
        FileBasicInfo fbi = new FileBasicInfo(fi.getSourceIpAddr(), fi.getFileSize(), fi.getCreateTimestamp());
        return fbi;
    }

    /**
     * 根据fileId获取文件上传时存储的MetaInfo
     * MetaInfo中至少包含以OriginFileName为key的原始文件名称和以OriginFileKeyName为key的文件扩展名
     * @param fileId
     * @return Map&lt;Sting, Sting$gt;结构的MetaInfo
     * @throws IOException
     * @throws MyException
     */
    public Map<String, String> getFileMetaInfo(String fileId) throws IOException, MyException {
        ClientGlobal.init(config.getConfigFile());
        Map<String, String> mapMetaInfos = new HashMap<>();
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;

        StorageClient1 storageClient = new StorageClient1(trackerServer,
                storageServer);
        NameValuePair nvps [] = storageClient.get_metadata1(fileId);
        if(nvps != null && nvps.length > 0) {
            for (NameValuePair nvp : nvps) {
                mapMetaInfos.put(nvp.getName(), nvp.getValue());
            }
        }
        return mapMetaInfos;
    }


}
