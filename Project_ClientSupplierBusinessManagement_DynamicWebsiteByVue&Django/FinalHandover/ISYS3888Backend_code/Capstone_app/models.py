from django.db import models
from django.contrib.auth.models import AbstractBaseUser

class Customer(models.Model):
    email = models.EmailField(null = True)
    first_name = models.CharField(max_length=30,null = True)
    last_name = models.CharField(max_length=30,null = True)
    phone_number = models.CharField(max_length=12,null = True)
    state = models.TextField(null = True)
    address1 = models.TextField(null = True)
    address2 = models.TextField(null = True)
    payment_method = models.CharField(max_length=30,null = True)
    image = models.ImageField(null = True)
    username = models.CharField(max_length=300)
    password = models.CharField(max_length=1000)
    token = models.CharField(null = True,max_length=500)


class Supplier(models.Model):
    image = models.ImageField(null = True)
    first_name = models.CharField(max_length=30,null = True)
    last_name = models.CharField(max_length=30,null = True)
    phone_number = models.CharField(max_length=12,null = True)
    email = models.EmailField(null = True)
    business_name = models.CharField(max_length=30,null = True)
    ABN = models.CharField(max_length=11,null = True)
    ACN = models.CharField(max_length=9,null = True)
    landline_no = models.IntegerField(null = True)
    address = models.TextField(null = True)
    username = models.CharField(max_length=30)
    password = models.CharField(max_length=1000)
    salt = models.CharField(max_length=300,null = True)
    iteration = models.IntegerField(null = True)
    token = models.CharField(null = True,max_length=500)
'''
class MoC(models.Model):
    MoC_id = models.IntegerField(primary_key=True)
    phone_number = models.CharField(max_length=12)
    email = models.EmailField()
'''

class Order(models.Model):
    title = models.TextField()
    customer_id = models.ForeignKey(Customer, on_delete=models.CASCADE,null=True)
    supplier_id = models.ForeignKey(Supplier, on_delete=models.CASCADE,null=True)
    date = models.DateTimeField()
    order_state = models.TextField() #in progress, completed, closed
    description = models.TextField()
    category = models.CharField(max_length=30)
    budget = models.FloatField()
    suburb = models.TextField()
    photo = models.BinaryField(null=True)
    video = models.BinaryField(null=True)

'''
class Comment(models.Model):
    order_id = models.ForeignKey(Order, on_delete=models.CASCADE)
    customer_id = models.ForeignKey(Customer, on_delete=models.CASCADE)
    comment = models.TextField()
'''

class Quote(models.Model):
    order_id = models.ForeignKey(Order, on_delete=models.CASCADE,null=True)
    supplier_id = models.ForeignKey(Supplier, on_delete=models.CASCADE,null=True)
    customer_id = models.ForeignKey(Customer, on_delete=models.CASCADE,null=True)
    budget = models.FloatField()
    order_state = models.TextField() #只能说pendding accept reject
    comment = models.CharField(max_length=100)
    date = models.DateTimeField(null=True)
    EditDate = models.DateTimeField(null=True)
    suburb = models.TextField()
    title = models.TextField()
    category = models.CharField(max_length=30)


class Message(models.Model):
    supplier_id = models.ForeignKey(Supplier, on_delete=models.CASCADE)
    customer_id = models.ForeignKey(Customer, on_delete=models.CASCADE)
    customer_name = models.CharField(max_length=30, null = True)
    sender = models.CharField(max_length=30, null = True)
    date = models.DateTimeField(null=True)
    content = models.TextField()
    last_conversion = models.BooleanField(null = True)
