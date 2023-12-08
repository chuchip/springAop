# springAop

Example of application in spring Boot 3.2 using  AOP.

## Endpoints:

### GET memory/{iterations}

Invoke the function getMemoryUsed. The function `logAll`  of the class CheckMemoryAspect is invoked.

If the parameter iterations is high, and you make a lot of calls, the use of memory can be enough bigger to throw a Exception.

#### Example:
> curl http://localhost:8080/memory/1000

### GET /something

Invoke the function doSomethingMore. In the log of the applicatión you can see that the function execute the function `logAll`  

#### Example:
> curl http://localhost:8080/something


### GET /somethingMore

Invoke the function doSomethingMore. In the log of the applicatión you can see that the function execute the function `logAll` and the function `aroundPoint1`

#### Example:
> curl http://localhost:8080/somethingMore
> 
### GET /something/{TEXT} 

Invoke the function `doSomethingWithParam`. In the log of the applicatión you can see that the function execute the function `logAll` and the function `aroundPoint1` of `LogAspect`
In the function `aroundPoint1` the  text sent is printed. If the text is "skip" the function `doSomethingWithParams` of `TestService` is not exeuted.

#### Example:
> curl http://localhost:8080/somethingMore/{TEXT}
