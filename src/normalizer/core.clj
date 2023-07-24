(ns normalizer.core
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.core :as core]
            [clojure.string :as str]
            [normalizer.utils :as utils])
  (:gen-class))

(defn write-stdout
  "Ensure that stdout is UTF-8 encoded"
  [s]
  (let [stdout (io/writer *out* :encoding "UTF-8")]
    (binding [*out* stdout]
      (println s))))

(defn output-row [r]
  (write-stdout (str/join "," r)))

(defn process-row [header row]
  ;; grab vals we may need for later
  (let [fooval (nth row (.indexOf header "FooDuration"))
        barval (nth row (.indexOf header "BarDuration"))]
    (mapv
     (fn [colname rowval]
       (let [rowval (utils/sanitize-unicode rowval)]
         (case colname
           "Timestamp" (utils/given-ts->eastern rowval)
           "Address" (utils/wrap-commas rowval)
           "ZIP" (utils/zip-with-leading rowval)
           "FullName"  (utils/wrap-commas (str/upper-case rowval))
           "FooDuration" (utils/hmsms->secs rowval)
           "BarDuration" (utils/hmsms->secs rowval)
           "TotalDuration" (+ (utils/hmsms->secs fooval)
                              (utils/hmsms->secs barval))
           "Notes" (utils/wrap-commas rowval)
           :else (utils/wrap-commas rowval))))
     header row)))

(defn parse-csv-file [file]
  (let [csv-data (with-open [reader (clojure.java.io/reader file)]
                   (doall (csv/read-csv reader)))
        header (first csv-data)
        transformed-data (mapv #(process-row header %) (rest csv-data))]
    (write-stdout (str/join "," header))
    (doseq [r transformed-data]
      (output-row r))))

(defn -main []
  (parse-csv-file (io/reader *in*)))

