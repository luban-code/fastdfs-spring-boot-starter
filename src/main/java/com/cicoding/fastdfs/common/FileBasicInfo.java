package com.cicoding.fastdfs.common;

import java.util.Date;

public class FileBasicInfo {

    public FileBasicInfo(){

    }
    public FileBasicInfo(String sourceIpAddr, long fileSize, Date createTimestamp){
        this.sourceIpAddr = sourceIpAddr;
        this.fileSize = fileSize;
        this.createTimestamp = createTimestamp;
    }

    /**
     * 文件上传的第一个storage的ip
     */
    private String sourceIpAddr;
    /**
     * 单位是byte
     */
    private long fileSize;
    /**
     * 文件创建时间
     */
    private Date createTimestamp;

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getSourceIpAddr() {
        return sourceIpAddr;
    }

    public void setSourceIpAddr(String sourceIpAddr) {
        this.sourceIpAddr = sourceIpAddr;
    }
}
