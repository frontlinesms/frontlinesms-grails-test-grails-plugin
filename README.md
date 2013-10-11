# DONE

## 0.16

* Added TODO methods to integration tests
* Added public date formatting and parsing methods to the Echo message source
* Added new EchoMessageSource.getMessage() implementation to work around grails requirement for `<g:message error="...">`

## 0.15

* Updated date formatting to handle `date.format` key as well as `default.date.format` as required by g:formatDate tag.

## 0.14

* Added default date formatting to EchoMessageSource

## 0.13

* Added EchoMessageSource, which allows tests to use i18n codes instead of relying on any particular language

## 0.12

* Updated Selenium dependency version
* Added [Bails](https://github.com/frontlinesms/bails) to project

## 0.11

* Removed annoying output from Events script when running unit tests

## 0.10

* re-release of 0.9 with proper tagging

## 0.9

* reverted changes to metaClassModifiers detection as new implementation was broken

## 0.8

* Fixed integration test cleaning for H2 database

## 0.7

* Fixed database cleaning between integration tests

## 0.6

* Fixed MetaClassModifiers loading

## 0.5

* move between-test database cleansing for integration tests to this plugin

## 0.4

* move spec generation scripts to this plugin
* move injection of TODO method for specs into this plugin

## 0.3

* move junit report style stuff to this plugin
* move screenshot generation to this plugin

## 0.2

* move multi-browser gebspec to this plugin

## 0.1

* move test/quality dependencies to this plugin - geb, spock, selenium, code coverage, codenarc

# IN PROGRESS

* Create common root GebSpec

# TODO

