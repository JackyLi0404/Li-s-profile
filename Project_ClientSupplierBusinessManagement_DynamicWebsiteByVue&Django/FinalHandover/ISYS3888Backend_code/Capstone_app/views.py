from rest_framework import status
from django.contrib.auth import authenticate, login,logout
from .models import Supplier,Order, Message, Customer,Quote
from django.http import HttpResponseRedirect,HttpResponse
from django.shortcuts import render
from django.contrib import messages
from rest_framework.views import APIView
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
import json
import time
import random
import string
import datetime



@csrf_exempt
def login_site(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST' :
        data = request.body.decode("UTF-8")
        obj = json.loads(data)
        tusername = obj["username"]
        if Supplier.objects.filter(username = tusername):
            obj = Supplier.objects.get(username = tusername)
            ret['salt'] = obj.salt
            ret['iteration'] = obj.iteration
            ret['status'] = status.HTTP_200_OK
            return JsonResponse(ret)
        else:
            ret['status'] = status.HTTP_400_BAD_REQUEST
            return JsonResponse(ret)
    return JsonResponse(ret) 


@csrf_exempt
def login_cof(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST' :
       data = request.body.decode("UTF-8")
       obj = json.loads(data)
       tusername = obj["username"]
       tpassword = obj["password"]
       if Supplier.objects.filter(username = tusername):
           obje = Supplier.objects.get(username = tusername)
           if obje.password == tpassword:
               ret['status'] = status.HTTP_200_OK
               ret['token'] = obje.token
               return JsonResponse(ret)
           else:
               ret['status'] = status.HTTP_400_BAD_REQUEST
               return JsonResponse(ret)
    return JsonResponse(ret)


@csrf_exempt
def signup_site(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST' :
        data = request.body.decode("UTF-8")
        obj = json.loads(data)
        tusername = obj["username"]
        tpassword = obj["password"]
        tsalt = obj["salt"]
        titeration = obj["iteration"]
        if Supplier.objects.filter(username = tusername):
            ret['status'] = status.HTTP_400_BAD_REQUEST
            return JsonResponse(ret)
        else:
            Supplier.objects.create(
                username=tusername,
                password=tpassword,
                salt=tsalt,
                iteration=titeration,
                token = ''.join(random.sample(string.ascii_letters + string.digits, 8)),
            )  
            ret['status'] = status.HTTP_200_OK
            ret['token'] = Supplier.objects.get(username = tusername).token
            return JsonResponse(ret)
    return JsonResponse(ret)     
    
    
@csrf_exempt
def createOrder(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST' :
        data = request.body.decode("UTF-8")
        obj = json.loads(data)
        Order.objects.create(
            supplier_id = Supplier.objects.filter(token = obj['token']).first(),
            customer_id = Customer.objects.filter(id = 5).first(),
            date = obj["date"],
            order_state = obj["order_state"],
            title = obj["title"],
            description = obj["description"],
            category = obj["category"],
            budget = obj["budget"],
            suburb = obj["suburb"],
            )
        ret[status] = status.HTTP_200_OK
    return JsonResponse(ret)

@csrf_exempt
def createCustomer(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST' :
        data = request.body.decode("UTF-8")
        obj = json.loads(data)
        Customer.objects.create(
            first_name = obj["first_name"],
        )
    return JsonResponse(ret)

@csrf_exempt
def createMessage(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST' :
        data = request.body.decode("UTF-8")
        obj = json.loads(data)
        Message.objects.create(
            supplier_id = Supplier.objects.filter(token = obj['token']).first(),
            customer_id = Customer.objects.filter(first_name = obj['customer_name']).first(),
            customer_name = obj["customer_name"],
            sender = obj["sender"],
            date = obj["date"],
            content = obj["content"],
        )
    return JsonResponse(ret)

@csrf_exempt
def get_order_profile(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST':
        data = request.body.decode("UTF-8")
        obje = json.loads(data)
        user_token = obje["token"]
        if Supplier.objects.filter(token = user_token):
            user_id = Supplier.objects.get(token = user_token).id
            lst = Order.objects.filter(supplier_id = user_id).order_by("date")
            ret['status'] = status.HTTP_200_OK
            ret['data'] = list(lst.values())
            return JsonResponse(ret)
        else:
            ret['status'] = status.HTTP_400_BAD_REQUEST
            return JsonResponse(ret)
    return JsonResponse(ret)



@csrf_exempt
def get_all_job(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'GET':
        lst1 = Order.objects.filter(order_state__in = ["unallocated","pending"]).order_by("date")
        ret['status'] = status.HTTP_200_OK
        ret['data'] = list(lst1.values())
        return JsonResponse(ret)
    return JsonResponse(ret)



@csrf_exempt
def save_quote(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST':
        data = request.body.decode("UTF-8")
        obje = json.loads(data)
        user_token = obje["token"]
        if Supplier.objects.filter(token = user_token):
            user_id = Supplier.objects.get(token = user_token).id
            lst = Quote.objects.filter(supplier_id = user_id)
            llst = lst.filter(order_state__in = ["pending","accepted","reject"]).order_by("date")
            ret['status'] = status.HTTP_200_OK
            ret['data'] = list(llst.values())
            return JsonResponse(ret)
        else:
            ret['status'] = status.HTTP_400_BAD_REQUEST
            return JsonResponse(ret)
    return JsonResponse(ret)


@csrf_exempt
def notification_site(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST':
        data = request.body.decode("UTF-8")
        obje = json.loads(data)
        user_token = obje["token"]
        if Supplier.objects.filter(token = user_token):
            user_id = Supplier.objects.get(token = user_token).id
            lst = Quote.objects.filter(supplier_id = user_id)
            llst = lst.filter(order_state__in = ["completed","work in progress"]).order_by("date")
            ret['status'] = status.HTTP_200_OK
            ret['data'] = list(llst.values())
            return JsonResponse(ret)
        else:
            ret['status'] = status.HTTP_400_BAD_REQUEST
            return JsonResponse(ret)
    return JsonResponse(ret)



@csrf_exempt
def createNewOrder(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST' :
        data = request.body.decode("UTF-8")
        obj = json.loads(data)
        Quote.objects.create(
            supplier_id = Supplier.objects.filter(token = obj['token']).first(),
            date = obj["date"],
            title = obj["title"],
            comment = obj["description"],
            category = obj["category"],
            budget = obj["budget"],
            suburb = obj["suburb"],
            order_state = "pending"
            )
        ret["status"] = status.HTTP_200_OK    
    return JsonResponse(ret)
    
@csrf_exempt
def createQuote(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST' :
        data = request.body.decode("UTF-8")
        obj = json.loads(data)
        Quote.objects.create(
            supplier_id = Supplier.objects.filter(token = obj['token']).first(),
            date = obj["date"],
            title = obj["title"],
            comment = obj["description"],
            category = obj["category"],
            budget = obj["budget"],
            suburb = obj["suburb"],
            order_state = obj['order_state']
            )
        ret["status"] = status.HTTP_200_OK    
    return JsonResponse(ret)
    
    
@csrf_exempt
def update_profile(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST' :
        data = request.body.decode("UTF-8")
        obj = json.loads(data)
        user_token = obj["token"]
        if Supplier.objects.filter(token = user_token):
            objec = Supplier.objects.get(token = user_token)
            objec.last_name = obj["lastname"]
            objec.first_name = obj["firstname"]
            objec.business_name = obj["businessname"]
            objec.ABN = obj["ABN"]
            objec.ACN = obj["ACN"]
            objec.address = obj["address"]
            objec.save()
            ret["status"] = status.HTTP_200_OK
            return JsonResponse(ret)
        else:
            ret['status'] = status.HTTP_400_BAD_REQUEST
            return JsonResponse(ret)
    return JsonResponse(ret)

@csrf_exempt
def new_messages(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST' :
        data = request.body.decode("UTF-8")
        obj = json.loads(data)
        user_token = obj["token"]
        if ( (Supplier.objects.filter(token = user_token)) and (not Message.objects.filter(customer_id = obj['customer_id'])) ):
        #if ( (Supplier.objects.filter(token = user_token))):
            Message.objects.create(
                supplier_id = Supplier.objects.filter(token = obj['token']).first(),
                customer_id = Customer.objects.filter(id = obj['customer_id']).first(),
                customer_name = Customer.objects.get(id = obj['customer_id']).first_name,
                content = "The supplier is intersted in your order, you can now have a communication with him/her!",
                sender = "Supplier",
                date = obj["date"],
            )
            ret["status"] = status.HTTP_200_OK
            return JsonResponse(ret)
        else:
            ret['status'] = status.HTTP_400_BAD_REQUEST
            return JsonResponse(ret)
        
    return JsonResponse(ret)
    
@csrf_exempt
def initial_messages(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST' :
        data = request.body.decode("UTF-8")
        obj = json.loads(data)
        user_token = obj["token"]
        if Supplier.objects.filter(token = user_token):
            user_id = Supplier.objects.get(token = user_token).id
            lst = Message.objects.filter(supplier_id = user_id).order_by("-date")
            ret["status"] = status.HTTP_200_OK
            ret['data'] = list(lst.values())
            return JsonResponse(ret)
        else:
            ret['status'] = status.HTTP_400_BAD_REQUEST
            return JsonResponse(ret)
    return JsonResponse(ret)

@csrf_exempt
def give_messages(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST':
        data = request.body.decode("UTF-8")
        obj = json.loads(data)
        user_token = obj["token"]
        if Supplier.objects.filter(token = user_token):
            user_id = Supplier.objects.get(token = user_token).id
            lst = Message.objects.filter(supplier_id = user_id, customer_id = obj['customer_id'])
            ret["status"] = status.HTTP_200_OK
            ret['data'] = list(lst.values())
            return JsonResponse(ret)
        else:
            ret['status'] = status.HTTP_400_BAD_REQUEST
            return JsonResponse(ret)
    return JsonResponse(ret)
    

@csrf_exempt
def get_messages(request):
    ret = {'status': status.HTTP_304_NOT_MODIFIED}
    if request.method == 'POST' :
        data = request.body.decode("UTF-8")
        obj = json.loads(data)
        user_token = obj["token"]
        if Supplier.objects.filter(token = user_token):
            objec = Customer.objects.get(id = obj['customer_id'])
            Message.objects.create(
                supplier_id = Supplier.objects.filter(token = obj['token']).first(),
                customer_id = Customer.objects.filter(id = obj['customer_id']).first(),
                customer_name = objec.first_name,
                sender = "Supplier",
                date = obj["timestamp"],
                content = obj["text"]
            )
            ret["status"] = status.HTTP_200_OK
            return JsonResponse(ret)
        else:
            ret['status'] = status.HTTP_400_BAD_REQUEST
            return JsonResponse(ret)
    return JsonResponse(ret)

def home(request):
    return render(request,'home.html')  