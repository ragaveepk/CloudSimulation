package Simulations

import Simulations.SimulationThree.conf
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfter
import org.slf4j.{Logger, LoggerFactory}

class Simulation3Testsuite extends AnyFlatSpec with Matchers {
  behavior of "configuration parameters module"

  it should "obtain the utilization ratio" in {
    conf.getDouble("simulation_3"+".utliziationRatio") shouldBe 0.5
  }

  it should "obtain the MIPS capacity" in {
    conf.getLong("simulation_3.vm.mips") shouldBe 5000
  }
}
class Simulation1Testsuite extends AnyFunSuite with BeforeAndAfter {
  val simulation_one = "simulation_1";

  val conf: Config = ConfigFactory.load(simulation_one + ".conf")
  val LOG: Logger = LoggerFactory.getLogger(getClass)

  test(" .createDatacenter") {

    val name = "datacenter"
    // function call to datacenter creation
    val datacenter = SimulationOne.createDataCenter()

    val numOfHosts = conf.getInt( simulation_one + "." + "datacenter" + ".numOfHosts")

    LOG.debug("Testing whether data center got created successfully")
    // return true if the datacenter is not null
    println(assert(datacenter != null))

    LOG.debug("Testing whether the number of hosts matches the config")
    // return true if the number of hosts created is equal to hosts mentioned in the config file
    println(assert(datacenter.getHostList.size() == numOfHosts))
  }

  test("SimulationOne.createVms") {

    val num_vms = conf.getInt( simulation_one + "." + "numofVMs")

    // function call to VM creation
    val vmlist = SimulationOne.createVms
    // return true if the VMlist is not null
    LOG.debug("Testing whether vmlist not empty ")
    assert(vmlist.size() != 0)
    // return true if the number of VMs created is equal to Vms mentioned in the config file
    LOG.debug("Testing whether number of VMs matches config")
    assert(vmlist.size == num_vms)
  }

  test("SimulationOne.createHost") {

    val num_hosts = conf.getInt(simulation_one + "." + "datacenter" + ".numOfHosts")

    // function call to host creation
    val host = SimulationOne.createHosts
    // return true if the host is not null
    LOG.debug("Testing whether host not null ")
    assert(host != null)
    // return true if the sotrage is equal to storgae capacity mentioned in the config file
    LOG.debug("Testing whether host storage matches config")
    assert(host.getStorage.getCapacity == conf.getInt(simulation_one + "." + "host" + ".storage"))
  }

  test("SimulationOne.createCloudlets") {

    val num_cloudlets =  conf.getInt( simulation_one + "." + "numOfCloudlets")

    //function call to cloudlets creation
    val cloudlets = SimulationOne.createCloudlets

    LOG.debug("Testing whether cloudlets list not empty ")
    assert(cloudlets.size() != 0)
    // return true if the cloudlets size  created is equal to cloudlets number  mentioned in the config file
    LOG.debug("Testing whether cloudlets list size matches config")
    assert(cloudlets.size() == num_cloudlets)
  }

}
