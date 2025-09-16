import boto3

ec2 = boto3.client('ec2', region_name='us-east-1')  # choose your region

def create_instance():
    response = ec2.run_instances(
        ImageId='ami-0c55b159cbfafe1f0',  # example Amazon Linux 2 AMI
        InstanceType='t2.micro',
        MinCount=1,
        MaxCount=1
    )
    return response['Instances'][0]['InstanceId']

def list_instances():
    response = ec2.describe_instances()
    instances = []
    for reservation in response['Reservations']:
        for instance in reservation['Instances']:
            instances.append({
                'InstanceId': instance['InstanceId'],
                'State': instance['State']['Name']
            })
    return instances

def terminate_instance(instance_id):
    ec2.terminate_instances(InstanceIds=[instance_id])
    return f"Instance {instance_id} terminated."
