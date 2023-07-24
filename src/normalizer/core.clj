(ns normalizer.core
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.core :as core]
            [clojure.string :as str]
            [normalizer.utils :as utils])
  (:gen-class))

(defn to-stderr [s]
  ((binding [*out* *err*]
     (println s))))

(defn read-csv [file]
  (with-open [reader (io/reader file)]
    (doall (csv/read-csv reader))))

(defn write-stdout
  "Ensure that stdout is UTF-8 encoded"
  [data]
  (let [stdout (io/writer *out* :encoding "UTF-8")]
    (binding [*out* stdout]
      (println data)
      (flush))))

(defn process-row [header row]

  ;; grab vals we may need for later
  (let [fooidx (.indexOf header "FooDuration")
        fooval (nth row fooidx)
        baridx (.indexOf header "BarDuration")
        barval (nth row baridx)]
    (mapv
     (fn [colname rowval]
       (case colname
         "Timestamp" (utils/ts->iso-dt rowval)
         "Address" (str "\"" rowval "\"")
         "ZIP" (utils/zip-with-leading rowval)
         "FullName" (str "\"" rowval "\"")
         "FooDuration" (utils/hmsms->secs rowval)
         "BarDuration" (utils/hmsms->secs rowval)
         "TotalDuration" (+ (utils/hmsms->secs fooval)
                            (utils/hmsms->secs barval))
         "Notes" (str "\"" rowval "\"")
         :else rowval))
     header row)))

(defn output-row [r]
  ; TODO: make sure to wrap address and everything else in quotes
  (println (str/join ", " r)))

(defn parse-csv-file [file]
  (let [csv-data (with-open [reader (clojure.java.io/reader file)]
                   (doall (csv/read-csv reader)))
        header (first csv-data)
        transformed-data (mapv #(process-row header %) (rest csv-data))]
    (write-stdout (str/join ", " header))
    (map output-row
         transformed-data)))

(defn -main [& args]
  (doseq [line (line-seq (io/reader *in*))]
    (parse-csv-file line)))


(comment

  (parse-csv-file (io/resource "sample-broken-utf8.csv"))

  ;
  )