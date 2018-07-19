import { TestBed, inject } from '@angular/core/testing';

import { AddRecordService } from './add-record.service';

describe('AddRecordService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AddRecordService]
    });
  });

  it('should be created', inject([AddRecordService], (service: AddRecordService) => {
    expect(service).toBeTruthy();
  }));
});
