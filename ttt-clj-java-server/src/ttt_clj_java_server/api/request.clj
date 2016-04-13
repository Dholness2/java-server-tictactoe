(ns ttt-clj-java-server.api.request
  (:require [clojure.test :refer :all]
            [clojure.string :as str])
  (:import com.javawebserver.app.Request))

(defn new-request []
  (Request.))

(defn get-route [request]
  (.getRoute request))

(defn get-request [request]
  (.getRequest request))

(defn get-full-message [request]
  (.getMessage request))

(defn get-headers [request]
  (.getHeaders request))

(defn get-body [request]
  (.getBody request))

(defn get-params [request]
  (.getParams request))

(defn set-request [request message]
  (.getMessage request message))

(defn set-headers [request headers]
  (.setHeaders request headers))

(defn set-body [request body]
  (.setBody request body))
