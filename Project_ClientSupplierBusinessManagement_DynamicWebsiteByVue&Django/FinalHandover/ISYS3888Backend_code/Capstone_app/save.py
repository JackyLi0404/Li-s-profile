from django.contrib.auth import authenticate, login,logout
from .models import Supplier
from django.http import HttpResponseRedirect,HttpResponse
from django.shortcuts import render
from django.contrib import messages


def login_site(request):
    if request.method == 'POST' :
        tusername = request.POST.get('username',)
        tpassword = request.POST.get('password',)
        suser = authenticate(username = tusername, password = tpassword)
        if suser:
            login(request,suser)
            return HttpResponseRedirect('/home/')
        else:
            messages.error(request,"username or password incorrect")
            return render(request,'login.html')
    return render(request, 'login.html')

def logout_site(request):
    logout(request)
    return HttpResponseRedirect('/')

def signup_site(request):
    if request.method == 'POST' : 
        if not request.POST.get('username') or not request.POST.get('password') or not request.POST.get('cfpassword'):
            messages.error(request,"please fill all content")
            return render(request,'signup.html') 
        tusername = request.POST.get('username')
        tpassword = request.POST.get('password')
        cfpassword = request.POST.get('cfpassword')
        if Supplier.objects.filter(username = request.POST['username']):
            messages.error(request,"username has been used")
            return render(request,'signup.html')
        elif tpassword != cfpassword :
            messages.error(request,"Please check your password")
            return render(request, 'signup.html')
        else:
            Supplier.objects.create(
                username = tusername,
                password = tpassword
            )
            return render(request, 'login.html')
    return render(request, 'signup.html') 

def home(request):
    return render(request,'home.html')


