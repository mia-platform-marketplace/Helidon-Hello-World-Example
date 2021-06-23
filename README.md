# Helidon-Hello-World-Example


[![Build Status][github-actions-svg]][github-actions]

This walkthrough will explain you how to correctly create a microservice in Helidon that returns an hello message from the DevOps Console.

## Create a microservice

In order to do so, access to [Mia-Platform DevOps Console](https://console.cloud.mia-platform.eu/login), create a new project and go to the **Design** area.  
From the Design area of your project select _Microservices_ and then create a new one, you have now reached [Mia-Platform Marketplace](https://docs.mia-platform.eu/development_suite/api-console/api-design/marketplace/)!  
In the marketplace you will see a set of Examples and Templates that can be used to set-up microservices with a predefined and tested function.  

For this walkthrough select the following example: **Helidon Hello World**.
Give your microservice the name you prefer, in this walkthrough we'll refer to it with the following name: **helidon-hello**. Then, fill the other required fields and confirm that you want to create a microservice.  
A more detailed description on how to create a Microservice can be found in [Microservice from template - Get started](https://docs.mia-platform.eu/development_suite/api-console/api-design/custom_microservice_get_started/#2-service-creation) section of Mia-Platform documentation.

> :warning:
> Please verify **CPU and memory limits**, for Java applications using Helidon we recommend at least:
> * Memory: minimum **100Mi**, maximum **300Mi**
> * CPU: minimum **10m**, maximum **500m**

### Resources and Performance
By running autocannon on the `/hello` route, we obtain the following performance: 

> autocannon http://localhost:3000/hello
Running 10s test @ http://localhost:3000/hello
10 connections

| |Stat|2.5%|50%|97.5%|99%|Avg|Stdev|Max|
|-|----|----|---|-----|---|---|-----|---|
|Non-native image|Latency|51 [ms]|95 [ms]|379 [ms]|543 [ms]|109.82 [ms]|87.04 [ms]|823 [ms]|
|Native image|Latency|47 [ms]|57 [ms]|71 [ms]|94 [ms]|58.12 [ms]|11.61 [ms]|214 [ms]|
<br />

| |Stat|1%|2.5%|50%|97.5%|Avg|Stdev|Min|
|-|----|--|----|---|-----|---|-----|---|
|Non-native image|Req/Sec|24|24|100|133|90.4|32.76|24|
| |Bytes/Sec|3.79 [kB]|3.79 [kB]|15.8 [kB]|21 [kB]|14.3 [kB]|5.18 [kB]|3.79 [kB]|
|Native image|Req/Sec|151|151|172|175|170.2|6.73|151|
| |Bytes/Sec|23.9 [kB]|23.9 [kB]|27.2 [kB]|27.7 [kB]|26.9 [kB]|1.06 [kB]|23.9 [kB]|
<br />

Non-native image: 904 requests in 10.05s, 143 kB read
<br />
Native image: 2k requests in 10.03s, 269 kB read

with the following consumption of resources:
| |CPU|Memory|
|-|---|------|
|Non-native|66 [mCPU]|75 [Mi]|
|Native|5 [mCPU]|32 [Mi]|
<br />

Therefore, the native image attends about twice the number of requests while consuming much less memory and cpu.

## Expose an endpoint to your microservice

In order to access to your new microservice it is necessary to create an endpoint that targets it.  
In particular, in this walkthrough you will create an endpoint to your microservice *helidon-hello*. To do so, from the Design area of your project select _Endpoints_ and then create a new endpoint.
Now you need to choose a path for your endpoint and to connect this endpoint to your microservice. Give to your endpoint the following path: **/hello**. Then, specify that you want to connect your endpoint to a microservice and, finally, select *helidon-hello*.  
Step 3 of [Microservice from template - Get started](https://docs.mia-platform.eu/development_suite/api-console/api-design/custom_microservice_get_started/#3-creating-the-endpoint) section of Mia-Platform documentation will explain in detail how to create an endpoint from the DevOps Console.

## Save your changes

After having created an endpoint to your microservice you should save the changes that you have done to your project in the DevOps console.  
Remember to choose a meaningful title for your commit (e.g "created service helidon_hello"). After some seconds you will be prompted with a popup message which confirms that you have successfully saved all your changes.  
Step 4 of [Microservice from template - Get started](https://docs.mia-platform.eu/development_suite/api-console/api-design/custom_microservice_get_started/#4-save-the-project) section of Mia-Platform documentation will explain how to correctly save the changes you have made on your project in the DevOps console.

## Deploy

Once all the changes that you have made are saved, you should deploy your project through the DevOps Console. Go to the **Deploy** area of the DevOps Console.  
Once here select the environment and the branch you have worked on and confirm your choices clicking on the *deploy* button. When the deploy process is finished you will receveive a pop-up message that will inform you.  
Step 5 of [Microservice from template - Get started](https://docs.mia-platform.eu/development_suite/api-console/api-design/custom_microservice_get_started/#5-deploy-the-project-through-the-api-console) section of Mia-Platform documentation will explain in detail how to correctly deploy your project.

## Try it

Now, if you launch the following command on your terminal (remember to replace `<YOUR_PROJECT_HOST>` with the real host of your project):

```shell
curl <YOUR_PROJECT_HOST>/hello
```

you should see the following message:

```json
{"message":"Hello World!"}
```

or if you want to try with a specific name:

```shell
curl <YOUR_PROJECT_HOST>/hello/Joe
```

you should see the following message:

```json
{"message":"Hello Joe!"}
```

Congratulations! You have successfully learnt how to use our Helidon _Hello World_ Example on the DevOps Console!

[github-actions]: https://github.com/mia-platform-marketplace/Helidon-Hello-World-Example/actions
[github-actions-svg]: https://github.com/mia-platform-marketplace/Helidon-Hello-World-Example/workflows/Java%20CI%20with%20Maven/badge.svg
