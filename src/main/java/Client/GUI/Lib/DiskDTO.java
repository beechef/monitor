/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.GUI.Lib;

/**
 *
 * @author Admin
 */
public class DiskDTO {
    private String DiskName;
    private String DiskTotalSize;
    private String DiskUsedSize;
    private String DiskAvailableSize;
    private String Space;

    public DiskDTO(String DiskName, String DiskTotalSize, String DiskUsedSize, String DiskAvailableSize, String Space) {
        this.DiskName = DiskName;
        this.DiskTotalSize = DiskTotalSize;
        this.DiskUsedSize = DiskUsedSize;
        this.DiskAvailableSize = DiskAvailableSize;
        this.Space = Space;
    }

    @Override
    public String toString() {
        return "DiskDTO{" + "DiskName=" + DiskName + ", DiskTotalSize=" + DiskTotalSize + ", DiskUsedSize=" + DiskUsedSize + ", DiskAvailableSize=" + DiskAvailableSize + ", Space=" + Space + '}';
    }
    
    

    public String getDiskName() {
        return DiskName;
    }

    public String getDiskTotalSize() {
        return DiskTotalSize;
    }

    public String getDiskUsedSize() {
        return DiskUsedSize;
    }

    public String getDiskAvailableSize() {
        return DiskAvailableSize;
    }

    public String getSpace() {
        return Space;
    }

    public void setDiskName(String DiskName) {
        this.DiskName = DiskName;
    }

    public void setDiskTotalSize(String DiskTotalSize) {
        this.DiskTotalSize = DiskTotalSize;
    }

    public void setDiskUsedSize(String DiskUsedSize) {
        this.DiskUsedSize = DiskUsedSize;
    }

    public void setDiskAvailableSize(String DiskAvailableSize) {
        this.DiskAvailableSize = DiskAvailableSize;
    }

    public void setSpace(String Space) {
        this.Space = Space;
    }
    
}
