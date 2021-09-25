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

import collection.JavaConverters.seqAsJavaListConverter
import collection.JavaConverters.asJavaIterableConverter
import java.util
import scala.jdk.javaapi.CollectionConverters.asJava
/**
A simple cloudsim plus example which uses defined configurations that has single datacenter, 10 hosts 20 vms and 30 cloudlets.
This simulation is uses Vm Allocation Bestfit, VmSchedulerTimeShared, CloudletSchedulerTimeShared and Utilization model as Stochastic
*/
object SimulationFour {

  val  simulation_one = "simulation_1"
  val conf: Config = ConfigFactory.load( simulation_one + ".conf")

  val LOG: Logger = LoggerFactory.getLogger(getClass)

  val simulation = new CloudSim
  val simpleBroker = new DatacenterBrokerSimple(simulation)
  val simpleDataCenter: DatacenterSimple = createDatacenter()

  simpleBroker.submitVmList(createVms)
  simpleBroker.submitCloudletList(createCloudlets)
  simulation.start
  new CloudletsTableBuilder(simpleBroker.getCloudletFinishedList).addColumn(new TextTableColumn("Actual CPU Time"), (cloudlet: Cloudlet) =>  BigDecimal(cloudlet.getActualCpuTime).setScale(2, BigDecimal.RoundingMode.HALF_UP)).addColumn(new TextTableColumn("Total Cost"), (cloudlet: Cloudlet) =>  BigDecimal(cloudlet.getTotalCost).setScale(2, BigDecimal.RoundingMode.HALF_UP)).build()

  LOG.info("The Simulation has ended.")


/**
 *This function create a simple  Datacenter along with charateristics like cost,cost per bw, cost per storage, cost per memory and its Hosts.
 */

  def createDatacenter(): DatacenterSimple = {
    LOG.info("Entered create datacenter function")

    //creates list of hosts
    val hostList_new = (1 to conf.getInt( simulation_one + "." + "datacenter" + ".numOfHosts")).map(host => createHost).toList

    val dataCenter = new DatacenterSimple(simulation, hostList_new.asJava, new VmAllocationPolicyBestFit )
    dataCenter.getCharacteristics.setCostPerMem(conf.getInt( simulation_one + "." + "datacenter" + ".costPerMemory")).setCostPerBw(conf.getInt( simulation_one + "." + "datacenter" + ".costPerBandwidth"))
      .setCostPerSecond(conf.getInt( simulation_one + "." + "datacenter" + ".cost")).setCostPerStorage(conf.getInt( simulation_one + "." + "datacenter" + ".costPerStorage"))
    dataCenter
  }


/**
 *This function creates a list of Hosts which includes  processing elements.
 */

  def createHost: Host = {

    val HOST_PES: Int = conf.getInt( simulation_one + "." + "host" + ".pes")
    val peList = (1 to HOST_PES).map(pe => new PeSimple(1000)).toList
    val ram = conf.getInt( simulation_one + "." + "host" + ".ram")
    val bw = conf.getInt( simulation_one + "." + "host" + ".bw")
    val storage = conf.getInt( simulation_one + "." + "host" + ".storage")

    new HostSimple(ram, bw, storage, asJava[Pe](peList)).setVmScheduler(new VmSchedulerTimeShared)
  }


/**
  *This function creates list of  virutal machines and its required pes and set Clouldlet Scheduler as Space shared
 */

  def createVms: util.List[Vm] = {

    val NUMOFVMS = conf.getInt( simulation_one + "." + "numofVMs")
    val VM_PES: Int = conf.getInt( simulation_one + "." + "vm" + ".pes")

    val list = (1 to NUMOFVMS).map(vm => new VmSimple(1000, VM_PES).setCloudletScheduler(new CloudletSchedulerTimeShared)).toList
    list.asJava
  }


/**
 *Creates a list of Cloudlets which including cloudlets length,  PEs required and utilization model.
 */

  def createCloudlets: util.List[CloudletSimple] = {

    val NUMOFCLOUDLETS: Int = conf.getInt( simulation_one + "." + "numOfCloudlets")
    val CLOUDLETLENGTH: Int = conf.getInt( simulation_one + "." + "cloudlet" + ".length")
    val NUMOFCLOUDLETPES: Int = conf.getInt( simulation_one + "." + "cloudlet" + ".pes")

    val list = (1 to NUMOFCLOUDLETS).map(c => new CloudletSimple(CLOUDLETLENGTH, NUMOFCLOUDLETPES, new UtilizationModelStochastic())).toList
    list.asJava
  }

  def main(args: Array[String]): Unit = {
    SimulationFour
  }
}
