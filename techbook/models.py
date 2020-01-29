from django.db import models
from django.contrib.auth.models import User
from django.contrib.auth.models import AbstractUser
from django.conf import settings
from rest_framework.authtoken.models import Token
from django.dispatch import receiver
from django.db.models.signals import post_save
from django.dispatch import receiver
from django.http import HttpResponse
import xlrd
from decimal import Decimal
import datetime

MATHIMATA_CHOICES = (
    ('sae','ΣΥΣΤΗΜΑΤΑ ΑΥΤΟΚΙΝΗΤΟΥ'),
    ('psiksi', 'ΨΥΞΗ ΚΑΙ ΚΛΙΜΑΤΙΣΜΟΣ'),
    ('ergaliomihanes','ΕΡΓΑΛΕΙΟΜΗΧΑΝΕΣ')
)

EIDOS_CHOICES = (
    ('pb','ΠΡΟΦΟΡΙΚΟΣ'),
    ('gp', 'ΓΡΑΠΤΟΣ'),
    ('diagonisma','ΔΙΑΓΩΝΙΣΜΑ'),
    ('eksetasis','ΕΞΕΤΑΣΕΙΣ')
)

# This code is triggered whenever a new user has been created and saved to the database
@receiver(post_save, sender=settings.AUTH_USER_MODEL)
def create_auth_token(sender, instance=None, created=False, **kwargs):
    if created:
        Token.objects.create(user=instance)


class Mathitis(AbstractUser):
    pass
    created = models.DateTimeField(auto_now_add=True)

    onoma = models.CharField('Ονομα και επώνυμο ', max_length=100, blank=True, default='')
    tmima = models.CharField('Τμήμα', max_length=100, blank=True, default='')
    mathima = models.CharField('Μαθημα', max_length=100, blank=True, default='')
    prosopiko_minima = models.CharField('Προσωπικό Μήνυμα', max_length=300, blank=True, default='')

    def __str__(self):
        return self.onoma

    class Meta:
        ordering = ('onoma',)
        verbose_name = 'Μαθητής'
        verbose_name_plural = 'Μαθητές'




class apusies(models.Model):


    giamathiti = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.PROTECT)
    date_apusias = models.DateField('Ημερομηνία', blank=True, null=True)
    no_apusion = models.IntegerField('Απουσίες', default='', null=True)

    def __str__(self):
        return str(self.date_apusias) + " - " + self.giamathiti.onoma + " - " + str(self.no_apusion)


    class Meta:
        ordering = ('date_apusias',)
        verbose_name = 'Απουσία'
        verbose_name_plural = 'Απουσίες'



class epidosis(models.Model):
    giamathiti2 = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.PROTECT)
    giamathima =  models.CharField('Μάθημα',max_length=6, choices=MATHIMATA_CHOICES, default='sae')
    date_epidosis = models.DateField('Ημερομηνία',blank=True, null=True)
    epidosi = models.IntegerField('Επίδοση', default='', null=True)
    eidos = models.CharField(max_length=66, choices=EIDOS_CHOICES, default='pb')

    def __str__(self):
       return str(self.epidosi) + str(self.giamathima) + " - " + self.giamathiti2.onoma


    class Meta:
        ordering = ('date_epidosis',)
        verbose_name = 'Επίδοση'
        verbose_name_plural = 'Επιδόσεις'




class simiosis(models.Model):
    giamathiti3 = models.CharField(max_length=116, choices=MATHIMATA_CHOICES, default='sae')
    date_simiosis = models.DateField(blank=True, null=True)
    simiosi = models.FileField(upload_to="techbook/static/simiosis", blank=True)

    class Meta:
        ordering = ('date_simiosis',)
        verbose_name = 'Σημείωση'
        verbose_name_plural = 'Σημειώσεις'

    def __str__(self):
        return str(self.giamathiti3) + " - " + str(self.simiosi)


class links(models.Model):
    giamathiti4 = models.CharField(max_length=116, choices=MATHIMATA_CHOICES, default='sae')
    date_links = models.DateField(blank=True, null=True)
    link = models.URLField('Σύνδεσμος ', max_length=100, blank=True, default='')

    class Meta:
        ordering = ('date_links',)
        verbose_name = 'Link'
        verbose_name_plural = 'Links'

    def __str__(self):
        return str(self.date_links) + " - " + self.giamathiti4 + " - " + str(self.link)