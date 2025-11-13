import { useState } from "react";
import { Upload, X } from "lucide-react";

export function AddNewsSection() {
  const [newsForm, setNewsForm] = useState({
    title: "",
    content: "",
    category: "",
    class: "",
    date: "",
  });

  const [uploadedFiles, setUploadedFiles] = useState<File[]>([]);

  const handleFileUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const newFiles = Array.from(e.target.files);
      setUploadedFiles([...uploadedFiles, ...newFiles]);
    }
  };

  const removeFile = (index: number) => {
    setUploadedFiles(uploadedFiles.filter((_, i) => i !== index));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("News submitted:", newsForm);
    console.log("Files uploaded:", uploadedFiles);
    // Reset form
    setNewsForm({ title: "", content: "", category: "", class: "", date: "" });
    setUploadedFiles([]);
  };

  return (
    <div className="bg-white rounded-lg shadow-sm p-8">
      <h2 className="text-gray-900 mb-6">Add News Announcement</h2>
      
      <form onSubmit={handleSubmit} className="space-y-6">
        <div className="space-y-4">
          <div>
            <label className="block text-gray-700 mb-2">Title</label>
            <input
              type="text"
              value={newsForm.title}
              onChange={(e) => setNewsForm({ ...newsForm, title: e.target.value })}
              placeholder="Enter news title"
              className="w-full px-4 py-3 bg-[#f5f5f5] border border-gray-200 rounded focus:outline-none focus:border-[#b899d4]"
            />
          </div>

          <div>
            <label className="block text-gray-700 mb-2">Category</label>
            <select
              value={newsForm.category}
              onChange={(e) => setNewsForm({ ...newsForm, category: e.target.value })}
              className="w-full px-4 py-3 bg-[#f5f5f5] border border-gray-200 rounded focus:outline-none focus:border-[#b899d4]"
            >
              <option value="">Select category</option>
              <option value="general">General</option>
              <option value="academic">Academic</option>
              <option value="event">Event</option>
              <option value="urgent">Urgent</option>
            </select>
          </div>

          <div>
            <label className="block text-gray-700 mb-2">Class</label>
            <select
              value={newsForm.class}
              onChange={(e) => setNewsForm({ ...newsForm, class: e.target.value })}
              className="w-full px-4 py-3 bg-[#f5f5f5] border border-gray-200 rounded focus:outline-none focus:border-[#b899d4]"
            >
              <option value="">Select class (optional)</option>
              <option value="all">All Classes</option>
              <option value="class1">Class 1</option>
              <option value="class2">Class 2</option>
              <option value="class3">Class 3</option>
              <option value="class4">Class 4</option>
              <option value="class5">Class 5</option>
            </select>
          </div>

          <div>
            <label className="block text-gray-700 mb-2">Date</label>
            <input
              type="date"
              value={newsForm.date}
              onChange={(e) => setNewsForm({ ...newsForm, date: e.target.value })}
              className="w-full px-4 py-3 bg-[#f5f5f5] border border-gray-200 rounded focus:outline-none focus:border-[#b899d4]"
            />
          </div>

          <div>
            <label className="block text-gray-700 mb-2">Content</label>
            <textarea
              value={newsForm.content}
              onChange={(e) => setNewsForm({ ...newsForm, content: e.target.value })}
              placeholder="Enter news content"
              rows={6}
              className="w-full px-4 py-3 bg-[#f5f5f5] border border-gray-200 rounded focus:outline-none focus:border-[#b899d4] resize-none"
            />
          </div>

          <div>
            <label className="block text-gray-700 mb-2">Attach Files/Photos</label>
            <div className="space-y-3">
              <label className="flex items-center justify-center w-full px-4 py-8 bg-[#f5f5f5] border-2 border-dashed border-gray-300 rounded cursor-pointer hover:bg-[#ede9f0] transition-colors">
                <div className="text-center">
                  <Upload className="w-8 h-8 text-gray-400 mx-auto mb-2" />
                  <span className="text-gray-600">Click to upload files</span>
                  <p className="text-gray-400 text-sm mt-1">Images, PDFs, Documents</p>
                </div>
                <input
                  type="file"
                  multiple
                  accept="image/*,.pdf,.doc,.docx"
                  onChange={handleFileUpload}
                  className="hidden"
                />
              </label>

              {uploadedFiles.length > 0 && (
                <div className="space-y-2">
                  {uploadedFiles.map((file, index) => (
                    <div
                      key={index}
                      className="flex items-center justify-between px-4 py-2 bg-[#e8dff0] rounded"
                    >
                      <span className="text-gray-700 truncate">{file.name}</span>
                      <button
                        type="button"
                        onClick={() => removeFile(index)}
                        className="text-gray-500 hover:text-red-500 transition-colors"
                      >
                        <X className="w-4 h-4" />
                      </button>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        </div>

        <div className="flex justify-end gap-3">
          <button
            type="button"
            onClick={() => {
              setNewsForm({ title: "", content: "", category: "", class: "", date: "" });
              setUploadedFiles([]);
            }}
            className="px-6 py-2 bg-gray-200 text-gray-700 rounded hover:bg-gray-300 transition-colors"
          >
            Cancel
          </button>
          <button
            type="submit"
            className="px-6 py-2 bg-[#b899d4] text-white rounded hover:bg-[#a888c4] transition-colors"
          >
            Publish News
          </button>
        </div>
      </form>
    </div>
  );
}