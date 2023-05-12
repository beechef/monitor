/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.GUI.Lib;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HardwareDTO {
    //cpu
    private String cpuClockSpeed;
    private String cpuUsagePercent;
    private String cpuLogicalProcessorCount;
    private String cpuPhysicalProcessorCount;    
    private String cpuProcessCount;
    private String cpuThreadCount;
    //memory
    private String TotalMemory;
    private String UsedMemory;    
    private String AvailableMemory;
    //disk
    public static List<DiskDTO> Disks = new ArrayList<>();

    public HardwareDTO() {
        Disks.removeAll(Disks);
    }
    
    

    public HardwareDTO( String cpuClockSpeed, String cpuUsagePercent, String cpuLogicalProcessorCount, String cpuPhysicalProcessorCount, String cpuProcessCount, String cpuThreadCount, String TotalMemory, String UsedMemory, String AvailableMemory) {
        this.cpuClockSpeed = cpuClockSpeed;
        this.cpuUsagePercent = cpuUsagePercent;
        this.cpuLogicalProcessorCount = cpuLogicalProcessorCount;
        this.cpuPhysicalProcessorCount = cpuPhysicalProcessorCount;
        this.cpuProcessCount = cpuProcessCount;
        this.cpuThreadCount = cpuThreadCount;
        this.TotalMemory = TotalMemory;
        this.UsedMemory = UsedMemory;
        this.AvailableMemory = AvailableMemory;
    }

  

    public String getCpuClockSpeed() {
        return cpuClockSpeed;
    }

    public String getCpuUsagePercent() {
        return cpuUsagePercent;
    }

    public String getCpuLogicalProcessorCount() {
        return cpuLogicalProcessorCount;
    }

    public String getCpuPhysicalProcessorCount() {
        return cpuPhysicalProcessorCount;
    }

    public String getCpuProcessCount() {
        return cpuProcessCount;
    }

    public String getCpuThreadCount() {
        return cpuThreadCount;
    }


    public String getTotalMemory() {
        return TotalMemory;
    }

    public String getUsedMemory() {
        return UsedMemory;
    }

    public String getAvailableMemory() {
        return AvailableMemory;
    }

    public static List<DiskDTO> getDisks() {
        return Disks;
    }

  

    public void setCpuClockSpeed(String cpuClockSpeed) {
        this.cpuClockSpeed = cpuClockSpeed;
    }

    public void setCpuUsagePercent(String cpuUsagePercent) {
        this.cpuUsagePercent = cpuUsagePercent;
    }

    public void setCpuLogicalProcessorCount(String cpuLogicalProcessorCount) {
        this.cpuLogicalProcessorCount = cpuLogicalProcessorCount;
    }

    public void setCpuPhysicalProcessorCount(String cpuPhysicalProcessorCount) {
        this.cpuPhysicalProcessorCount = cpuPhysicalProcessorCount;
    }

    public void setCpuProcessCount(String cpuProcessCount) {
        this.cpuProcessCount = cpuProcessCount;
    }

    public void setCpuThreadCount(String cpuThreadCount) {
        this.cpuThreadCount = cpuThreadCount;
    }

 

    public void setTotalMemory(String TotalMemory) {
        this.TotalMemory = TotalMemory;
    }

    public void setUsedMemory(String UsedMemory) {
        this.UsedMemory = UsedMemory;
    }

    public void setAvailableMemory(String AvailableMemory) {
        this.AvailableMemory = AvailableMemory;
    }

    public static void setDisks(List<DiskDTO> Disks) {
        HardwareDTO.Disks = Disks;
    }

    @Override
    public String toString() {
        return "HardwareDTO{" + "cpu="+ ", cpuClockSpeed=" + cpuClockSpeed + ", cpuUsagePercent=" + cpuUsagePercent + ", cpuLogicalProcessorCount=" + cpuLogicalProcessorCount + ", cpuPhysicalProcessorCount=" + cpuPhysicalProcessorCount + ", cpuProcessCount=" + cpuProcessCount + ", cpuThreadCount=" + cpuThreadCount + ", MEMORY "  + ", TotalMemory=" + TotalMemory + ", UsedMemory=" + UsedMemory + ", AvailableMemory=" + AvailableMemory + "Disks="+Disks.toString()+'}';
    }
    
    
    
    

}
