package Simulations


import HelperUtils.ObtainConfigReference
import com.typesafe.config.{Config, ConfigFactory}
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicyBestFit, VmAllocationPolicyRoundRobin, VmAllocationPolicySimple}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.schedulers.cloudlet.CloudletSchedulerSpaceShared
import org.cloudbus.cloudsim.schedulers.vm.{VmSchedulerSpaceShared, VmSchedulerTimeShared}
import org.cloudbus.cloudsim.utilizationmodels.{UtilizationModelDynamic, UtilizationModelFull, UtilizationModelStochastic}
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}
import org.cloudsimplus.builders.tables.{CloudletsTableBuilder, TextTableColumn}
import org.slf4j.{Logger, LoggerFactory}

import collection.JavaConverters.seqAsJavaListConverter
import collection.JavaConverters.asJavaIterableConverter
import java.util
import scala.jdk.javaapi.CollectionConverters.asJava
import collection.JavaConverters.asScalaBufferConverter
import collection.JavaConverters.collectionAsScalaIterableConverter
import collection.JavaConverters.iterableAsScalaIterableConverter

/**
A cloudsim plus example which uses defined configurations that has 2 datacenters, 15 vms and 30 cloudlets.
this simulation is uses Vm Allocation Simple, VmSchedulerSpaceShared, CloudletSchedulerSpaceShared
and utilization model as UtilizationModelDynamic with utilization Ratio as 0.5
*/


object SimulationTwo {
  val  simulation_2 = "simulation_2"
  val config: Config = ConfigFactory.load( simulation_2 + ".conf")

  val LOG: Logger = LoggerFactory.getLogger(getClass)
 
  //fetches the list og datacenters from configuraion file
  val conf = config.getConfigList(simulation_2+"."+"datacenters")
  val simulation = new CloudSim
  val brokerSimple = new DatacenterBrokerSimple(simulation)

  // iterate over the list of datacenter by creating and configuring dataceneters and their respective hosts
  (1 to conf.size).map(dc =>createDataCenter(conf.get(dc-1), simulation))

  // creation and submission of Vms and Cloudlets to the broker
  brokerSimple.submitVmList(createVms(config.getInt(simulation_2+"."+ "numOfVms")))
  brokerSimple.submitCloudletList(createCloudlets(config.getInt(simulation_2+"."+"numsOfCloudlets")))

  simulation.start
  new CloudletsTableBuilder(brokerSimple.getCloudletFinishedList).addColumn(new TextTableColumn("Actual CPU Time"), (cloudlet: Cloudlet) =>  BigDecimal(cloudlet.getActualCpuTime).setScale(2, BigDecimal.RoundingMode.HALF_UP)).addColumn(new TextTableColumn("Total Cost"), (cloudlet: Cloudlet) =>  BigDecimal(cloudlet.getTotalCost).setScale(2, BigDecimal.RoundingMode.HALF_UP)).build()

  LOG.info("The Simulation has ended.")


  def createDataCenter(dc:Config, simulation: CloudSim):Unit  = {
    
    //creates list of hosts
    val hostList_new = (1 to dc.getInt("numOfHosts")).map(host => createHost).toList
    val dataCenter = new DatacenterSimple(simulation, hostList_new.asJava, new VmAllocationPolicySimple )
    dataCenter.getCharacteristics.
      setCostPerMem(dc.getInt( "costPerMemory"))
      .setCostPerBw(dc.getInt("costPerBandwidth"))
      .setCostPerSecond(dc.getInt("cost"))
      .setCostPerStorage(dc.getInt("costPerStorage"))

  }

 /**
   * This function creates  Hosts which includes  processing elements and sets the VMscheduler
   */
  def createHost: Host = {

    val HOST_PES: Int = config.getInt(simulation_2+"."+ "hosts"+ "."+"pes")
    val peList = (1 to HOST_PES).map(pe => new PeSimple(1000)).toList
    val ram = config.getInt(simulation_2+"."+ "hosts"+ "."+"ram")
    val bandwidth = config.getInt(simulation_2+"."+ "hosts"+ "."+"bandwidth")
    val storage =  config.getInt(simulation_2+"."+ "hosts"+ "."+"storage")

    new HostSimple(ram, bandwidth, storage, asJava[Pe](peList))
      .setVmScheduler(new VmSchedulerSpaceShared)


  }

 /***
   * this functions creates list of  virutal machines and its required pes and sets the Clouldlet Scheduler
   */
  def createVms(NUMOFVMS:Int): util.List[Vm] = {
    val list = (1 to NUMOFVMS).map(vm =>
      val VM_PES = config.getInt(simulation_2+"."+ "vms"+ "."+"pes")
      val VM_MIPS =  config.getInt(simulation_2+"."+ "vms"+ "."+"mips")
      new VmSimple(VM_MIPS, VM_PES).setCloudletScheduler(new CloudletSchedulerSpaceShared)).toList
    list.asJava
  }

 /***
   * Creates a list of Cloudlets and utilization model
   */
  def createCloudlets(NUMOFCLOUDLETS:Int): util.List[CloudletSimple] = {

      val list = (1 to NUMOFCLOUDLETS).map(c =>
        val CLOUDLETLENGTH: Int = config.getInt(simulation_2+"."+ "cloudlets"+ "."+"length")
        val NUMOFCLOUDLETPES: Int = config.getInt(simulation_2+"."+ "cloudlets"+ "."+"pes")
        new CloudletSimple(CLOUDLETLENGTH, NUMOFCLOUDLETPES,
          new UtilizationModelDynamic(0.5))).toList
      list.asJava
  }

  def main(args: Array[String]): Unit = {
  SimulationTwo
}
  }