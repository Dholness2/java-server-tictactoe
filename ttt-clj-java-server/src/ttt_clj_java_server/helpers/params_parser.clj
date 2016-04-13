(ns ttt-clj-java-server.helpers.params-parser
  (:require [clojure.string :as str]))

(def filled-position 4)
(def player-move "openPosition=")

(defn parse-params [params]
  (str/split params #"&"))

(defn get-move [params]
  (let[params-list (parse-params params)
       move-selection (filter #(.contains % player-move) params-list)]
    (prn move-selection)
    (read-string (last (str/split (apply str move-selection) #"=")))))

(defn get-board-positions [params]
  (filter #(> filled-position (.length %)) (vec (parse-params params))))
