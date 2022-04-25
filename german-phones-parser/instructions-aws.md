# Kubernetes on AWS

Prerequisites
- Install and Configure AWS CLI `aws configure`
- Install EKSCTL - https://eksctl.io/introduction/installation/ `eksctl version`
- EKS is not free. t2.medium is not in the free tier!


### Create EKS Cluster

Help for eksctl https://eksctl.io/

```
eksctl create cluster --name navivsh-cluster --nodegroup-name navivsh-cluster-node-group  --node-type t2.medium --nodes 3 --nodes-min 3 --nodes-max 7 --managed --asg-access
```

If you get this error
```
AWS::EKS::Cluster/ControlPlane: CREATE_FAILED – "Cannot create cluster 
'navivsh-cluster' because us-east-1e, the targeted availability zone, does not 
currently have sufficient capacity to support the cluster. Retry and choose from these 
availability zones: us-east-1a, us-east-1b, us-east-1c, us-east-1d, us-east-1f (Service: 
	AmazonEKS; Status Code: 400; Error Code: UnsupportedAvailabilityZoneException; Request 
	ID: a5580928-689d-4558-b3bd-2573131ec69e)
```

Add Availability Zones

```
--zones=us-east-1a,us-east-1b
```

Things to Note
- VPCs and SubNets are Created

### Deploying Applications

```
kubectl apply -f german-phones-parser/kubernetes/deployment.yml
```

### Delete Applications

```
kubectl delete all -l app=hello-world-rest-api
kubectl delete all -l app=todowebapp-h2
kubectl delete all -l io.kompose.service=todo-web-application
kubectl delete all -l io.kompose.service=mysql
```

### Adding Ingress

- https://docs.aws.amazon.com/eks/latest/userguide/alb-ingress.html
- Check out sample application - https://raw.githubusercontent.com/kubernetes-sigs/aws-alb-ingress-controller/v1.1.4/docs/examples/2048/2048-ingress.yaml

```
kubectl apply -f german-phones-parser/kubernetes/.yaml
```

### Adding Application Insights

- https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/Container-Insights-setup-EKS-quickstart.html
- Attach CloudWatchAgentServerPolicy to the nodes

### Exploring Cluster Auto Scaler

- https://docs.aws.amazon.com/eks/latest/userguide/cluster-autoscaler.html

Testing auto scaler
```
kubectl create deployment autoscaler-demo --image=nginx
kubectl scale deployment autoscaler-demo --replicas=50
```

### Clean up the Cluster

https://docs.aws.amazon.com/eks/latest/userguide/delete-cluster.html

Delete all LoadBalancer services
```
kubectl get svc --all-namespaces
kubectl delete svc service-name
```

Delete the cluster
```
eksctl delete cluster --name navivsh-cluster
```
