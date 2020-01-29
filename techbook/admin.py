from django.contrib import admin
from django.contrib.auth.admin import UserAdmin

from .models import Mathitis, apusies, epidosis, simiosis, links

admin.site.site_header = "Διαχείριση App "
admin.site.site_title = "Techbook Admin Portal"
admin.site.index_title = "Καλως Ήλθατε"

class CustomUserAdmin(UserAdmin):

    model = Mathitis
    fieldsets = UserAdmin.fieldsets + (
        (None, {'fields': ('onoma','tmima','prosopiko_minima')}),
    )
    list_display = ['onoma', 'username', 'tmima']

admin.site.register(Mathitis, CustomUserAdmin)
#admin.site.register(Eksoda)
#admin.site.register(Esoda)
#admin.site.register(Dapanes)
#admin.site.register(apusies)
#admin.site.register(epidosis)
#admin.site.register(simiosis)
#admin.site.register(links)





# admin.py
from django.contrib import admin

class MyModelAdmin(admin.ModelAdmin):

       list_display = ('giamathiti','date_apusias')
    #   search_fields = ['date_apusias', 'giapelati2__eponimia__icontains']

admin.site.register(apusies, MyModelAdmin)



class MyModelAdmin2(admin.ModelAdmin):

       list_display = ('giamathiti2','date_epidosis')
     #  search_fields = ['date_epidosis', 'giapelati5__eponimia__icontains']

admin.site.register(epidosis, MyModelAdmin2)


class MyModelAdmin3(admin.ModelAdmin):

       list_display = ('giamathiti3','date_simiosis')
      # search_fields = ['date_ekkatharistikoy', 'giapelati5__eponimia__icontains']

admin.site.register(simiosis, MyModelAdmin3)

class MyModelAdmin4(admin.ModelAdmin):

       list_display = ('giamathiti4','date_links')
       search_fields = ['date_links', 'giapelati5__eponimia__icontains']

admin.site.register(links)




