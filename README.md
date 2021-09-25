# Homework 1

## Introduction
This homework consists in creating different simulations using Cloud Sim Plus, on datacenters with multiple hosts,vms, cloudlets and 
analyze the results obtained;
The simulation code has been written in Scala and can be compiled using SBT.

## How to Run
Please follow the following steps to run the simulations
Use IntelliJ IDEA with the Scala plugin installed along with sbt. 
-  “Clone https://github.com/ragaveepk/CloudSimulation.git" and Open Intellij IDEA 
- Proceed with default options by clicking 'OK' in the SBT import screen that pops up
- Confirm overwriting with “Yes” Please go to src/main/scala/Simulations and start running the simulation of your choice. 
- An IntelliJ run configuration  is auto-created when you click the green arrow next to the main method 
of the simulation file you want to run.

## Documentation
After Cloning the project , please find the Scaladoc documentation in docs folder.

## Reference 
- [CloudSim](https://javadoc.io/doc/org.cloudsimplus/cloudsim-plus/latest/index.html)

## Project Structure
### Simulations (src/main/scala/Simulations)
The following Simulation classes written in Scala using Cloud Sim Plus Framework are provided -
- SimulationOne - Single DataCenter
- SimulationTwo - Two Datacenters
- SimulationThree - Saas, Paas, Iaas Implementation
- SimulationFour - single Datacenter


### Resources (src/main/resources)
This directory mainly contains the different configuration files used for the created simulations.

- simulation_1
- simulation_2
- simulation_3
- simulation_4

### Tests (src/main/test/scala)
The following test class is provided: 
 
 MySimulation1Test -The following methods are tested:
- createDatacenter: the test checks whether the created Datacenter is not null and that it contains the correct number of hosts.
- createVms: the test checks whether the list of VMs created is not empty and the number of VMs created matches the input configuration.
- createHost: the test checks whether an individual host object created during simulations is not null and it's properties match the input configuration.
- createCloudlets: the test checks whether the list of cloudlets created is not empty and the number of cloudlets created matches the input configuration.

Simualtion3Testsuite - The Behavior verification are tested
- check for utilization ratio → Fetches the Utilization Ratio from the configuration file and verifies whether the ratio is retrieved correctly
- check of MIPS capacity →  Fetches the MIPs of the VM from the configuration file and verifies whether the value is retrieved correctly


## Configuration
Specific configuration files are used for individual simulations.
Configuration parameters for the following entities are used:
- Datacenter
- Host
- VM
- Cloudlet

The following are the parameters used for MySimulation1, MySimulation2, MySimulation3 -
SimulationOne uses
- Number of Datacenters: 1
- Operating Systems: Linux
- Number of Hosts :10
- Number of PEs: 8
- RAM : 8192
- Number of VMs : 20
- Number of PEs: 4
- RAM : 512
- Number of Cloudlets : 30
- Number of PEs: 4
- Length : 1000

Pricing Model:
- Cost per MB of Memory: $ 0.05
- Cost per MB of Storage: $ 0.1
- Cost per Megabit of Bandwidth: $0.8
- Cost per Second of CPU Use: $ 2.5

SimulationTwo uses
- Number of Datacenters: 2
#### DataCenter 1: Operating Systems: Linux
- Number of hosts: 5
- Pricing Model:
- Cost per MB of Memory: $0.05
- Cost per MB of Storage: $ 1.0
- Cost per Megabit of Bandwidth: $0.9
- Cost per Second of CPU Use: $2.5


#### DataCenter 2:  Operating Systems: Windows
- Number of hosts: 100
- Pricing Model:
- Cost per MB of Memory: $0.5
- Cost per MB of Storage: $ 1.0
- Cost per Megabit of Bandwidth: $0.9
- Cost per Second of CPU Use: $3.5


- Number of Host PEs: 8
- Host RAM : 8192
- Number of VMs : 20
- Number of PEs: 4
- RAM: 512
- Number of Cloudlets : 30
- Number of PEs: 4
- Length: 1000


SimualtionThree uses
Number of Datacenters: 3

DataCenter 1 → Saas
- Number of Hosts: 10
- Number of Vms: 20
- Number of Cloudlets: 20
- Operating System: Linux
- Pricing Model:
- Cost per MB of Memory: $ 0.05
- Cost per MB of Storage: $ 0.1
- Cost per Megabit of Bandwidth: $0.8
- Cost per Second of CPU Use: $ 1.5


DataCenter 2→  Paas
- Number of Hosts: 25
- Number of Vms: 15
- Number of Cloudlets: 25
- Operating System: Linux
- Pricing Model:
- Cost per MB of Memory: $ 0.05
- Cost per MB of Storage: $ 0.2
- Cost per Megabit of Bandwidth: $0.8
- Cost per Second of CPU Use: $ 2.5

DataCenter 3 → Iaas
- Number of Hosts: 10
- Number of Vms: 20
- Number of Cloudlets: 20
- Operating System: {Linux, Windows, Ubuntu}
- Pricing Model:
- Cost per MB of Memory: $ 0.05
- Cost per MB of Storage: $ 0.1
- Cost per Megabit of Bandwidth: $0.8
- Cost per Second of CPU Use: $ 1.5


Host  PEs: 8
Host RAM: 8192

Vm PEs: 4
Vm RAM : 512


Cloudlet PEs: {1,2,4} 
Length: {100,200,500}

_one item in each PEs and length  will be chosen  from the lists using random function_


## Policies used in the Simulations are,
- VM Allocation Policy 
- Cloudlet Utilization Model
- Cloudlet Scheduler Type
- VM Scheduler Type



## Results Analysis

### MySimulation1:


SIMULATION RESULTS:

The below simulation output represents that some of the Cloudlets are made to wait until other cloudlets complete 
their execution as there is no enough PEs to allocate the cloudlets. This is because both scheduler  are
SpacesShared which doesn't share their allocated PEs with other VMs/ Cloudlets until the process is complete.
Once the Vms are done with execution of some of cloudlets, then cloudlets in the waiting list are allocated.


- VM Allocation Policy : Simple
- Cloudlet Utilization Model: Dynamic
- Cloudlet Scheduler Type: Space-Shared
- VM Scheduler Type: Space-Shared


                                        SIMULATION RESULTS

    Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime|Actual CPU Time|Total Cost
    ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds|               |
--------------------------------------------------------------------------------------------------------------------------------
       0|SUCCESS| 2|   0|        8| 0|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
       1|SUCCESS| 2|   0|        8| 1|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
       2|SUCCESS| 2|   1|        8| 2|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
       3|SUCCESS| 2|   1|        8| 3|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
       4|SUCCESS| 2|   2|        8| 4|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
       5|SUCCESS| 2|   2|        8| 5|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
       6|SUCCESS| 2|   3|        8| 6|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
       7|SUCCESS| 2|   3|        8| 7|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
       8|SUCCESS| 2|   4|        8| 8|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
       9|SUCCESS| 2|   4|        8| 9|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
      10|SUCCESS| 2|   5|        8|10|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
      11|SUCCESS| 2|   5|        8|11|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
      12|SUCCESS| 2|   6|        8|12|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
      13|SUCCESS| 2|   6|        8|13|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
      14|SUCCESS| 2|   7|        8|14|        4|      10000|          3|        0|        13|      13|          13.34|     26.69
      15|SUCCESS| 2|   0|        8| 0|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      16|SUCCESS| 2|   0|        8| 1|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      17|SUCCESS| 2|   1|        8| 2|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      18|SUCCESS| 2|   1|        8| 3|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      19|SUCCESS| 2|   2|        8| 4|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      20|SUCCESS| 2|   2|        8| 5|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      21|SUCCESS| 2|   3|        8| 6|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      22|SUCCESS| 2|   3|        8| 7|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      23|SUCCESS| 2|   4|        8| 8|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      24|SUCCESS| 2|   4|        8| 9|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      25|SUCCESS| 2|   5|        8|10|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      26|SUCCESS| 2|   5|        8|11|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      27|SUCCESS| 2|   6|        8|12|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      28|SUCCESS| 2|   6|        8|13|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
      29|SUCCESS| 2|   7|        8|14|        4|      10000|          3|       14|        26|      13|          13.44|     26.89
---------------------------------------------------------------------------------------------------------------------------------

### MySimulationFour:

SimulationFour result represents that Total cost  keeps increasing by time, this is because cloudlet utilization is Stochastic which uses random resources 
utilization every time frame and also schedulers are Time shared which allocates more than one PEs  from  host to Vm / Vm to Cloudlet. It basically shares 
the resources.In the below output, we can see that all the cloudlets start at the same time, but finish time varies which in turn affects in the total cost.

- VM Allocation Policy : BestFit
- Cloudlet Utilization Model: Stochastic
- Cloudlet Scheduler Type: Time-Shared
- VM Scheduler Type: Time-Shared
                                         

                                    SIMULATION RESULTS

    Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime|Actual CPU Time|Total Cost
    ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds|               |
--------------------------------------------------------------------------------------------------------------------------------
       7|SUCCESS| 2|   3|        8| 7|        4|      10000|          2|        0|        11|      11|          10.86|     21.73
      11|SUCCESS| 2|   5|        8|11|        4|      10000|          2|        0|        12|      12|          12.16|     24.32
       4|SUCCESS| 2|   2|        8| 4|        4|      10000|          2|        0|        12|      12|          12.38|     24.76
      14|SUCCESS| 2|   7|        8|14|        4|      10000|          2|        0|        15|      15|          14.93|     29.86
       5|SUCCESS| 2|   2|        8| 5|        4|      10000|          2|        0|        16|      16|          16.34|     32.68
       9|SUCCESS| 2|   4|        8| 9|        4|      10000|          2|        0|        16|      16|          16.45|     32.90
      13|SUCCESS| 2|   6|        8|13|        4|      10000|          2|        0|        18|      18|          17.82|     35.64
       6|SUCCESS| 2|   3|        8| 6|        4|      10000|          2|        0|        18|      18|          18.29|     36.58
      17|SUCCESS| 2|   1|        8| 2|        4|      10000|          2|        0|        21|      21|          21.00|     42.00
      10|SUCCESS| 2|   5|        8|10|        4|      10000|          2|        0|        21|      21|          21.00|     42.00
       8|SUCCESS| 2|   4|        8| 8|        4|      10000|          2|        0|        21|      21|          21.22|     42.44
      12|SUCCESS| 2|   6|        8|12|        4|      10000|          2|        0|        21|      21|          21.44|     42.88
       1|SUCCESS| 2|   0|        8| 1|        4|      10000|          2|        0|        22|      22|          22.42|     44.84
       0|SUCCESS| 2|   0|        8| 0|        4|      10000|          2|        0|        23|      23|          22.90|     45.79
       2|SUCCESS| 2|   1|        8| 2|        4|      10000|          2|        0|        25|      25|          25.34|     50.67
      23|SUCCESS| 2|   4|        8| 8|        4|      10000|          2|        0|        25|      25|          25.34|     50.67
      16|SUCCESS| 2|   0|        8| 1|        4|      10000|          2|        0|        27|      27|          27.02|     54.04
      24|SUCCESS| 2|   4|        8| 9|        4|      10000|          2|        0|        27|      27|          27.02|     54.04
      27|SUCCESS| 2|   6|        8|12|        4|      10000|          2|        0|        27|      27|          27.02|     54.04
      15|SUCCESS| 2|   0|        8| 0|        4|      10000|          2|        0|        35|      35|          35.13|     70.25
       3|SUCCESS| 2|   1|        8| 3|        4|      10000|          2|        0|        35|      35|          35.13|     70.25
      19|SUCCESS| 2|   2|        8| 4|        4|      10000|          2|        0|        35|      35|          35.13|     70.25
      20|SUCCESS| 2|   2|        8| 5|        4|      10000|          2|        0|        35|      35|          35.13|     70.25
      21|SUCCESS| 2|   3|        8| 6|        4|      10000|          2|        0|        35|      35|          35.13|     70.25
      22|SUCCESS| 2|   3|        8| 7|        4|      10000|          2|        0|        35|      35|          35.13|     70.25
      26|SUCCESS| 2|   5|        8|11|        4|      10000|          2|        0|        35|      35|          35.13|     70.25
      18|SUCCESS| 2|   1|        8| 3|        4|      10000|          2|        0|       154|     154|         153.82|    307.64
      25|SUCCESS| 2|   5|        8|10|        4|      10000|          2|        0|       154|     154|         153.82|    307.64
      28|SUCCESS| 2|   6|        8|13|        4|      10000|          2|        0|       154|     154|         153.82|    307.64
      29|SUCCESS| 2|   7|        8|14|        4|      10000|          2|        0|       154|     154|         153.82|    307.64
--------------------------------------------------------------------------------------------------------------------------------

To summarize,the total cost is incurred in SimulationFour is high because Time shared scheduler shares the PEs and runs 
simultaneously -some of the process runs for a longer time increasing the cost   whereas Space Shared policy will not share the Pes thus it executes one after another, thus using space 
share scheduler yields lower total cost.


### MySimulationTwo:


This simulation uses 2 DataCenters with 5 hosts and 50 hosts.As we are using Space shared schedulers for both Vm and 
cloudlets, Datacenter has 5 hosts ,so it can accommodate some cloudlets only,while datacenter three has 50 hosts so it 
can get accommodate next some cloudlets in the queue.Remaining cloudlets get allocated once the there is enough Pes in


Vms in any one of the datacenters.
- VM Allocation Policy : Simple
- Cloudlet Utilization Model: Dynamic
- Cloudlet Scheduler Type: Space-Shared
- VM Scheduler Type: Space-Shared


                                    SIMULATION RESULTS

    Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime|Actual CPU Time|Total Cost
    ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds|               |
--------------------------------------------------------------------------------------------------------------------------------
       0|SUCCESS| 2|   0|        8| 0|        4|       1000|          4|        0|         2|       2|           2.00|      4.00
       5|SUCCESS| 2|   0|        8| 5|        4|       1000|          4|        0|         2|       2|           2.00|      4.00
       1|SUCCESS| 2|   1|        8| 1|        4|       1000|          4|        0|         2|       2|           2.00|      4.00
       6|SUCCESS| 2|   1|        8| 6|        4|       1000|          4|        0|         2|       2|           2.00|      4.00
       2|SUCCESS| 2|   2|        8| 2|        4|       1000|          4|        0|         2|       2|           2.00|      4.00
       7|SUCCESS| 2|   2|        8| 7|        4|       1000|          4|        0|         2|       2|           2.00|      4.00
       3|SUCCESS| 2|   3|        8| 3|        4|       1000|          4|        0|         2|       2|           2.00|      4.00
       8|SUCCESS| 2|   3|        8| 8|        4|       1000|          4|        0|         2|       2|           2.00|      4.00
       4|SUCCESS| 2|   4|        8| 4|        4|       1000|          4|        0|         2|       2|           2.00|      4.00
       9|SUCCESS| 2|   4|        8| 9|        4|       1000|          4|        0|         2|       2|           2.00|      4.00
      10|SUCCESS| 3|   0|        8|10|        4|       1000|          4|        0|         2|       2|           2.00|      6.00
      11|SUCCESS| 3|   1|        8|11|        4|       1000|          4|        0|         2|       2|           2.00|      6.00
      12|SUCCESS| 3|   2|        8|12|        4|       1000|          4|        0|         2|       2|           2.00|      6.00
      13|SUCCESS| 3|   3|        8|13|        4|       1000|          4|        0|         2|       2|           2.00|      6.00
      14|SUCCESS| 3|   4|        8|14|        4|       1000|          4|        0|         2|       2|           2.00|      6.00
      15|SUCCESS| 2|   0|        8| 0|        4|       1000|          4|        2|         4|       2|           2.11|      4.22
      20|SUCCESS| 2|   0|        8| 5|        4|       1000|          4|        2|         4|       2|           2.11|      4.22
      16|SUCCESS| 2|   1|        8| 1|        4|       1000|          4|        2|         4|       2|           2.11|      4.22
      21|SUCCESS| 2|   1|        8| 6|        4|       1000|          4|        2|         4|       2|           2.11|      4.22
      17|SUCCESS| 2|   2|        8| 2|        4|       1000|          4|        2|         4|       2|           2.11|      4.22
      22|SUCCESS| 2|   2|        8| 7|        4|       1000|          4|        2|         4|       2|           2.11|      4.22
      18|SUCCESS| 2|   3|        8| 3|        4|       1000|          4|        2|         4|       2|           2.11|      4.22
      23|SUCCESS| 2|   3|        8| 8|        4|       1000|          4|        2|         4|       2|           2.11|      4.22
      19|SUCCESS| 2|   4|        8| 4|        4|       1000|          4|        2|         4|       2|           2.11|      4.22
      24|SUCCESS| 2|   4|        8| 9|        4|       1000|          4|        2|         4|       2|           2.11|      4.22
      25|SUCCESS| 3|   0|        8|10|        4|       1000|          4|        2|         4|       2|           2.11|      6.33
      26|SUCCESS| 3|   1|        8|11|        4|       1000|          4|        2|         4|       2|           2.11|      6.33
      27|SUCCESS| 3|   2|        8|12|        4|       1000|          4|        2|         4|       2|           2.11|      6.33
      28|SUCCESS| 3|   3|        8|13|        4|       1000|          4|        2|         4|       2|           2.11|      6.33
      29|SUCCESS| 3|   4|        8|14|        4|       1000|          4|        2|         4|       2|           2.11|      6.33
--------------------------------------------------------------------------------------------------------------------------------




### SimulationThree

### Policy/Architecture implemented in SimulationThree

In this simulation, Based on the services attribute  mentioned in the simulation_3.conf file
(need to change manually)

- services = "IaaS" | "SaaS"| "PaaS"

In this Simulation,we assume **random function** as user input which varies based on the services mentioned
The services offered across 3 datacenters are modelled as per the following plan-
- DataCenter1 - Dedicated for SaaS (Software as a Service)
- DataCenter2 - Dedicated  for PaaS (Platform as a Service)
- DataCenter3 - Dedicated  for Iaas (Infrastructure as a Service)

The following logic is used to differentiate between the different data centers for simulating the services as per the
user requested configurations :

- If the services is  Saas, then  number of cloudlets is taken from the list using random function(consider it as user giving input) and
then datacenter1 is configured to Saas
- If the services is  Paas, then  number of cloudlets , number of PEs  is selected using random function is
taken from the list using random function and then datacenter2 is configured to Paas
- If the services is  Iaas, then  number of cloudlets , number of PEs  and Operating Systems
is selected using random function and then datacenter3 is configured to Paas
- 
Default
- VMAllocationPolicy: Simple
- Utilization model: Dynamic - utilization ratio as 0.5
- Cloudlet Scheduler Type:  Time-Shared
- VM Scheduler Type: Space-Shared


SaaS Output:

                                                 SIMULATION RESULTS

    Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime|Actual CPU Time|Total Cost
    ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds|               |
--------------------------------------------------------------------------------------------------------------------------------
       0|SUCCESS| 2|   0|        8| 0|        4|        100|          1|        0|         0|       0|           0.21|      0.21
       1|SUCCESS| 2|   1|        8| 1|        4|        100|          2|        0|         0|       0|           0.21|      0.21
       2|SUCCESS| 2|   2|        8| 2|        4|        100|          3|        0|         0|       0|           0.21|      0.21
       3|SUCCESS| 2|   3|        8| 3|        4|        100|          4|        0|         0|       0|           0.21|      0.21
       4|SUCCESS| 2|   4|        8| 4|        4|        100|          5|        0|         0|       0|           0.32|      0.32
       5|SUCCESS| 2|   5|        8| 5|        4|        100|          6|        0|         0|       0|           0.32|      0.32
       6|SUCCESS| 2|   6|        8| 6|        4|        100|          7|        0|         1|       0|           0.43|      0.43
       7|SUCCESS| 2|   7|        8| 7|        4|        100|          8|        0|         1|       0|           0.43|      0.43
       8|SUCCESS| 2|   8|        8| 8|        4|        100|          9|        0|         1|       1|           0.54|      0.54
       9|SUCCESS| 2|   9|        8| 9|        4|        100|         10|        0|         1|       1|           0.54|      0.54
--------------------------------------------------------------------------------------------------------------------------------


Paas Output:

                                                       SIMULATION RESULTS

    Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime|Actual CPU Time|Total Cost
    ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds|               |
--------------------------------------------------------------------------------------------------------------------------------
      10|SUCCESS| 2|  10|        8|10|        4|        100|         11|        0|         1|       1|           0.57|      1.13
      11|SUCCESS| 2|  11|        8|11|        4|        100|         12|        0|         1|       1|           0.68|      1.35
      12|SUCCESS| 2|  12|        8|12|        4|        100|         13|        0|         1|       1|           0.68|      1.35
      13|SUCCESS| 2|  13|        8|13|        4|        100|         14|        0|         1|       1|           0.79|      1.57
      14|SUCCESS| 2|  14|        8|14|        4|        100|         15|        0|         1|       1|           0.79|      1.57
       0|SUCCESS| 2|   0|        8| 0|        4|        100|          1|        0|         1|       1|           1.01|      2.01
      15|SUCCESS| 2|   0|        8| 0|        4|        100|         16|        0|         1|       1|           1.01|      2.01
       1|SUCCESS| 2|   1|        8| 1|        4|        100|          2|        0|         1|       1|           1.01|      2.01
      16|SUCCESS| 2|   1|        8| 1|        4|        100|         17|        0|         1|       1|           1.01|      2.01
       2|SUCCESS| 2|   2|        8| 2|        4|        100|          3|        0|         1|       1|           1.12|      2.23
      17|SUCCESS| 2|   2|        8| 2|        4|        100|         18|        0|         1|       1|           1.12|      2.23
       3|SUCCESS| 2|   3|        8| 3|        4|        100|          4|        0|         1|       1|           1.23|      2.45
      18|SUCCESS| 2|   3|        8| 3|        4|        100|         19|        0|         1|       1|           1.23|      2.45
       4|SUCCESS| 2|   4|        8| 4|        4|        100|          5|        0|         1|       1|           1.45|      2.89
      19|SUCCESS| 2|   4|        8| 4|        4|        100|         20|        0|         1|       1|           1.45|      2.89
       5|SUCCESS| 2|   5|        8| 5|        4|        100|          6|        0|         1|       1|           1.45|      2.89
      20|SUCCESS| 2|   5|        8| 5|        4|        100|         21|        0|         1|       1|           1.45|      2.89
       6|SUCCESS| 2|   6|        8| 6|        4|        100|          7|        0|         2|       2|           1.67|      3.33
      21|SUCCESS| 2|   6|        8| 6|        4|        100|         22|        0|         2|       2|           1.67|      3.33
       7|SUCCESS| 2|   7|        8| 7|        4|        100|          8|        0|         2|       2|           1.67|      3.33
      22|SUCCESS| 2|   7|        8| 7|        4|        100|         23|        0|         2|       2|           1.67|      3.33
       8|SUCCESS| 2|   8|        8| 8|        4|        100|          9|        0|         2|       2|           1.89|      3.77
      23|SUCCESS| 2|   8|        8| 8|        4|        100|         24|        0|         2|       2|           1.89|      3.77
       9|SUCCESS| 2|   9|        8| 9|        4|        100|         10|        0|         2|       2|           1.89|      3.77
      24|SUCCESS| 2|   9|        8| 9|        4|        100|         25|        0|         2|       2|           1.89|      3.77
--------------------------------------------------------------------------------------------------------------------------------

Iaas Output:
 
                                                SIMULATION RESULTS

    Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime|Actual CPU Time|Total Cost
    ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds|               |
--------------------------------------------------------------------------------------------------------------------------------
       0|SUCCESS| 2|   0|        8| 0|        4|        500|          1|        0|         1|       1|           1.11|      2.22
       1|SUCCESS| 2|   1|        8| 1|        4|        500|          2|        0|         1|       1|           1.11|      2.22
       2|SUCCESS| 2|   2|        8| 2|        4|        500|          3|        0|         1|       1|           1.11|      2.22
       3|SUCCESS| 2|   3|        8| 3|        4|        500|          4|        0|         1|       1|           1.11|      2.22
       4|SUCCESS| 2|   4|        8| 4|        4|        500|          5|        0|         1|       1|           1.33|      2.66
       5|SUCCESS| 2|   5|        8| 5|        4|        500|          6|        0|         2|       1|           1.55|      3.10
       6|SUCCESS| 2|   6|        8| 6|        4|        500|          7|        0|         2|       2|           1.88|      3.76
       7|SUCCESS| 2|   7|        8| 7|        4|        500|          8|        0|         2|       2|           2.10|      4.20
       8|SUCCESS| 2|   8|        8| 8|        4|        500|          9|        0|         2|       2|           2.32|      4.64
       9|SUCCESS| 2|   9|        8| 9|        4|        500|         10|        0|         3|       2|           2.54|      5.08
      10|SUCCESS| 2|  10|        8|10|        4|        500|         11|        0|         3|       3|           2.87|      5.74
      11|SUCCESS| 2|  11|        8|11|        4|        500|         12|        0|         3|       3|           3.09|      6.18
      12|SUCCESS| 2|  12|        8|12|        4|        500|         13|        0|         3|       3|           3.42|      6.84
      13|SUCCESS| 2|  13|        8|13|        4|        500|         14|        0|         4|       4|           3.64|      7.28
      14|SUCCESS| 2|  14|        8|14|        4|        500|         15|        0|         4|       4|           3.97|      7.94
--------------------------------------------------------------------------------------------------------------------------------

