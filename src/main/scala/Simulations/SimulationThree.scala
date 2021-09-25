package Simulations


import com.typesafe.config.{Config, ConfigFactory}
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicyBestFit, VmAllocationPolicyRoundRobin, VmAllocationPolicySimple}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.schedulers.cloudlet.{CloudletSchedulerSpaceShared, CloudletSchedulerTimeShared}
import org.cloudbus.cloudsim.schedulers.vm.{VmSchedulerSpaceShared, VmSchedulerTimeShared}
import org.cloudbus.cloudsim.utilizationmodels.{UtilizationModelDynamic, UtilizationModelFull, UtilizationModelStochastic}
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}
import org.cloudsimplus.builders.tables.{CloudletsTableBuilder, TextTableColumn}
import org.slf4j.{Logger, LoggerFactory}
import scala.jdk.CollectionConverters.*
import java.util
import scala.jdk.javaapi.CollectionConverters.asJava

/**
A  cloudsim plus example which uses defined configurations based ont services given ie SaaS, Paas,IaaS that has 3 datacenters
this simulation is uses Vm Allocation Simple, VmSchedulerSpaceShared, CloudletSchedulerTimeShared and utilization model as UtilizationModelDynamic with utilization Ratio as 0.5
*/


object SimulationThree {

  val simulation_3 = "simulation_3"
  val conf: Config = ConfigFactory.load(simulation_3 + ".conf")

  val CLOUDLET_PES =conf.getList(simulation_3+"."+"cloudlet"+"."+"pes")
  val VM_PES:Int = conf.getInt(simulation_3 + ".vm"+ ".pes")
  val List_Count :Int = CLOUDLET_PES.size()
  val CLOUDLET_LENGTH = conf.getList(simulation_3 + "." +"cloudlet"+".length")
  val services = conf.getString(simulation_3+"."+"services")
  val rand = scala.util.Random // random function which is assumed as user input
  val Default_Index: Int = 1 // for Saas, default value is taken from list

  val LOG: Logger = LoggerFactory.getLogger(getClass)

  val simulation = new CloudSim
  val broker0 = new DatacenterBrokerSimple(simulation)
  // based on the services match repective Saas, PaaS , IaaS function is called
   services match {
     case "SaaS"=> CreateSaasServices
     case "PaaS"=> CreatePaasServices
     case "IaaS"=> CreateIaasServices
   }
  /**
* This funtion to congifure th Saas datacenter
*/

  def CreateSaasServices:Unit = {
    LOG.info("Configuring SaaS Datacenter")

    val os = converttoString((conf.getList(simulation_3 + ".datacenter_1" + ".os")).get(rand.nextInt(Default_Index)).unwrapped())
    val CloudletLength = converttoInt(CLOUDLET_LENGTH.get(rand.nextInt(Default_Index)).unwrapped())
    val cloudPes =  converttoInt(CLOUDLET_PES.get(rand.nextInt(Default_Index)).unwrapped())

    configureDataCenter("datacenter_1",CloudletLength,os,cloudPes)
  }
  /**
  * This funtion to congifure th Paas datacenter
  */
  def CreatePaasServices: Unit = {
    LOG.info("Configuring PaaS DataCenter")

    val CloudLength = converttoInt(CLOUDLET_LENGTH.get(rand.nextInt(List_Count)).unwrapped())
    val os = converttoString((conf.getList(simulation_3 + ".datacenter_2" + ".os")).get(rand.nextInt(Default_Index)).unwrapped())
    val cloudPes =  converttoInt(CLOUDLET_PES.get(rand.nextInt(List_Count)).unwrapped())
    configureDataCenter("datacenter_2",CloudLength,os,cloudPes)
  }
  /**
  * This funtion to configure th Iaas datacenter
   */
  def CreateIaasServices: Unit ={
    LOG.info("Configuring IaaS DataCenter")

    val CloudLength = converttoInt(CLOUDLET_LENGTH.get(rand.nextInt(List_Count)).unwrapped())
    val os = converttoString((conf.getList(simulation_3 + ".datacenter_3" + ".os")).get(rand.nextInt(List_Count)).unwrapped())
    val cloudPes =  converttoInt(CLOUDLET_PES.get(rand.nextInt(List_Count)).unwrapped())
    configureDataCenter("datacenter_3",CloudLength,os,cloudPes)
  }

  simulation.start()

  val finishedCloudlets: util.List[Cloudlet] = broker0.getCloudletFinishedList
  new CloudletsTableBuilder(finishedCloudlets).addColumn(new TextTableColumn("Actual CPU Time"), (cloudlet: Cloudlet) =>  BigDecimal(cloudlet.getActualCpuTime).setScale(2, BigDecimal.RoundingMode.HALF_UP)).addColumn(new TextTableColumn("Total Cost"), (cloudlet: Cloudlet) =>  BigDecimal(cloudlet.getTotalCost).setScale(2, BigDecimal.RoundingMode.HALF_UP)).build()

  LOG.info("The Simulation has ended.")
/**
 *this function configures the Datacenter by creating hosts, vms,cloudlets and submitting to broker
*/
  def configureDataCenter(datacenter: String,CloudletLength :Int,os:String,CloudPes:Int): Unit = {

    val NumOfCloudlets = converttoInt(conf.getList(simulation_3 + "." + datacenter + ".numOfCloudlets").get(rand.nextInt(List_Count)).unwrapped())
    val VMS: Int = conf.getInt(simulation_3 + "."+ datacenter  + ".numOfVms")
    val arch = conf.getString(simulation_3 + "." +"datacenter_2" + "."+"arch")
    val vmm = conf.getString(simulation_3 + "." + datacenter + ".vmm")
    val cost_mem = conf.getInt(simulation_3 + "." +  datacenter + ".costPerMemory")
    val cost_Bw = conf.getInt(simulation_3 + "." + datacenter + ".costPerBandwidth")
    val cost = conf.getInt(simulation_3 + "." + datacenter + ".cost")
    val cost_storage = conf.getInt(simulation_3 + "." + datacenter + ".costPerStorage")
    val num_host = conf.getInt(simulation_3 + "." + datacenter + ".numOfHosts")
    val HostPes: Int = conf.getInt(simulation_3 + "." + "host" + ".pes")

    val vmList: util.List[Vm] = createVms(VMS)
    broker0.submitVmList(vmList)

    val cloudletList: util.List[Cloudlet] = createCloudlets(NumOfCloudlets, CloudletLength, CloudPes)
    broker0.submitCloudletList(cloudletList)
    createDataCenter(os,arch,vmm,cost_mem,cost_Bw,cost,cost_storage,num_host,HostPes)
  }
/**
* This function which creates datacenter and  sets OS,architecture,and pricing
*/
  def createDataCenter(OS:String,architecture:String,Vmm:String,costperMem:Int,costperBW:Int,
                       costPerSec:Int,costPerStorage:Int,numberofHosts :Int,HostPes: Int):Unit=
 

  {
    val host_List = (1 to numberofHosts).map(host => createHost(HostPes)).toList

    val dataCenter = new DatacenterSimple(simulation,host_List.asJava,new VmAllocationPolicySimple)
    dataCenter.setSchedulingInterval(0.5)
    dataCenter.getCharacteristics
      .setOs(OS)
      .setArchitecture(architecture)
      .setVmm(Vmm)
      .setCostPerMem(costperMem)
      .setCostPerBw(costperBW)
      .setCostPerSecond(costPerSec)
      .setCostPerStorage(costPerStorage)

  }
/**
* This function to convert Object to Integer
*/

  def converttoInt(s: Object) :Int ={
    s match {
      case n: java.lang.Integer => n
    }
  }
/**
*This function to convert Object to String
 */
  def converttoString(s1: Object) :String ={
    print("string is",s1)
    s1 match {
      case str: java.lang.String => str
    }

  }
/**
*this functioncreates the host along with processing enlements
 */
  def createHost(hostPES: Int): Host = {
    val peList = (1 to hostPES).map(pe => new PeSimple(1000)).toList

    new HostSimple(conf.getInt(simulation_3 + "." + "host" + ".ram"),
      conf.getInt(simulation_3 + "." + "host" + ".bw"),
      conf.getInt(simulation_3 + "." + "host" + ".storage"),
      asJava[Pe](peList))
      .setVmScheduler( new VmSchedulerSpaceShared())
  }

/**
*Create the list of Vms which sets size,bandwidth,ram and cloudletscheduler
 */
  def createVms(numOfVms:Int) : util.List[Vm] = {
    val list = (1 to numOfVms).map(vm =>
      new VmSimple(1000, VM_PES)
      .setSize(conf.getInt(simulation_3 + "." + "vm" + ".size"))
      .setBw(conf.getInt(simulation_3 + "." + "vm" + ".bw"))
      .setRam(conf.getInt(simulation_3 + "." + "vm" + ".ram"))

      .setCloudletScheduler(new CloudletSchedulerTimeShared)).toList

    list.asJava
  }

  /**
  *Create the list of cloudlets which including cloudlets length,  PEs required and utilization model
   */
  def createCloudlets(NUMOFCLOUDLETS:Int, CLOUDLETLENGTH:Int, NUMOFCLOUDLETPES:Int) :util.List[Cloudlet] =  {
    val utilizationratio   = conf.getDouble(simulation_3 + "." + "utliziationRatio")

    val list = (1 to NUMOFCLOUDLETS).map(NUMOFCLOUDLETPES => new CloudletSimple(
      CLOUDLETLENGTH,
      NUMOFCLOUDLETPES,
      new UtilizationModelDynamic(utilizationratio))).toList
    list.asJava

  }


  def main(args: Array[String]): Unit = {
    SimulationThree

  }
}