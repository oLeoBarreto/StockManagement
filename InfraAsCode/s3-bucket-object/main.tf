provider "aws" {
  access_key                  = "test"
  secret_key                  = "test"
  region                      = "us-east-1"
  s3_use_path_style           = true
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true

  endpoints {
    iam            = "http://localhost:4566"
    s3             = "http://s3.localhost.localstack.cloud:4566"
    kms            = "http://localhost:4566"
  }
}

resource "aws_s3_bucket" "bucket" {
  bucket = "images-stock-management-bucket"
}

resource "aws_kms_key" "kms-s3-files" {
  description = "KMS key 1"
  deletion_window_in_days = 7
}

resource "aws_s3_bucket_acl" "acl" {
  bucket = aws_s3_bucket.bucket.id
  acl    = "private"
}

resource "aws_s3_bucket_object" "images" {
  key                    = "images/stock-management-image"
  bucket                 = aws_s3_bucket.bucket.id
  source                 = "product/image"
  kms_key_id             = aws_kms_key.kms-s3-files.id
}
