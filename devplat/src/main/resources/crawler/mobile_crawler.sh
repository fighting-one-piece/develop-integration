#!/bin/bash

cd /home/ym/project/mobile_crawler
pwd
scrapy crawl mobile_spider -a mobile_number=$1
