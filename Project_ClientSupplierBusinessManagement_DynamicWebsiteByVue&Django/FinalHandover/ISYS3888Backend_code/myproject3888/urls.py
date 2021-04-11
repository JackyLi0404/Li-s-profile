"""myproject3888 URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django import urls
from django.urls import path, include
from Capstone_app import views
from django.conf.urls import url

urlpatterns = [
    path('admin/', admin.site.urls),
    path('Capstone_app/', include('Capstone_app.urls')),
    path('api-auth/', include('rest_framework.urls')),
    url(r'^login_site/$',views.login_site,),
    url(r'^signup_site/$',views.signup_site,),
    url(r'^login_cof/$',views.login_cof,),
    url(r'^createOrder/$',views.createOrder),
    url(r'^createCustomer/$',views.createCustomer),
    url(r'^createMessage/$',views.createMessage),
    url(r'^get_order_profile/$',views.get_order_profile,),
    url(r'^get_all_job/$',views.get_all_job,),
    url(r'^save_quote/$',views.save_quote),
    url(r'^createNewOrder/$',views.createNewOrder),
    url(r'^update_profile/$',views.update_profile),
    url(r'^notification_site/$',views.notification_site),
    url(r'^initial_messages/$',views.initial_messages),
    url(r'^new_messages/$',views.new_messages),
    url(r'^get_messages/$',views.get_messages),
    url(r'^give_messages/$',views.give_messages),
    url(r'^createQuote/$',views.createQuote),
    url(r'^$',views.home),
]
